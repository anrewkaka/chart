#!/bin/bash

# 引数チェック
if [ $# -ne 1 ]; then
    echo "引数が不正です。引数の個数：$#" 1>&2
    # 異常終了
    exit -1
fi

# ロードグループIDを取得
TARGET_LOAD_GROUPID=${1}

# 設定ファイル
. $(cd $(dirname $0); pwd)/gcp_dwh.env

# アカウント設定
gcloud auth activate-service-account ${SERVICE_ACCOUNT} \
    --key-file /opt/scripts/classes/${KEY_FILE} --project ${PROJECT_NAME}

# プロジェクト設定
gcloud config set project ${PROJECT_NAME}

# 日付管理テーブルからロード対象日を取得
for i in `seq 1 ${MAX_RETRY_COUNT}`; do

    # ロード対象日取得
    RETURN_STR=`bq --format csv query --use_legacy_sql=false "select FORMAT_TIMESTAMP('%Y%m%d', tday_ymd) as tday_ymd from honto.dwh_bt_ymd_mng order by bt_reg_dttm desc limit 1"`
    EXIT_STATUS=${?}

    # 正常終了時はループを抜ける
    if [ "${EXIT_STATUS}" -eq "0" ]; then
        break
    fi

    if [ ${i} -lt ${MAX_RETRY_COUNT} ]; then
        # エラー発生時はスリープして自動リトライ
        sleep ${RETRY_SLEEP_TIME}
    else
        # 最大リトライ数分実行後のエラーは異常終了
        echo "ロード対象日取得に失敗しました。${RETURN_STR}" 1>&2
        # 異常終了
        exit -1
    fi

done

# ロード対象日
LOAD_YMD=`echo ${RETURN_STR} | sed -e "s/[\n]\+//g" | awk '{print substr($0, 10)}'`

# コピー処理結果ログファイル作成
GCS_SEND_LOG=${LOCAL_BASEDIR}/log/GCS_SEND_${TARGET_LOAD_GROUPID}_`date +%Y%m%d%H%M%S`.log
touch ${GCS_SEND_LOG}

# GCSからコピー対象リストを取得
gsutil cp gs://${CONF_BUCKET}/list/rawdatalist.csv ${LOCAL_BASEDIR}/work/rawdatalist_${TARGET_LOAD_GROUPID}.csv
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

# リスト区切り文字
IFS=','

# リストに従ってコントロールファイルの存在チェック
while read line
do

    # コメント行や空行をスキップ
    result=`echo ${line} | tr -d "\r" | tr -d "\n"`
    if [ `echo ${result} | egrep "^#" | wc -l` -gt 0 ] || [ "${result}" = "" ]; then
        continue
    fi

    # カンマでsplit
    set -- ${line}

    REAL_KBN=${1}
    LOAD_GROUPID=${2}
    FROM_FILE_PATH=${4}
    FROM_FILE_NAME=${5}

    # 対象のロードグループID以外はスキップ
    if [ "${TARGET_LOAD_GROUPID}" != "${LOAD_GROUPID}" ]; then
        continue
    fi

    # コントロールファイルの存在をチェック
    FIND_CTL_FILE="${FROM_FILE_NAME}_*.ctl"
    LOCAL_CTL_FILE="`find ${FROM_FILE_PATH} -type f -name ${FIND_CTL_FILE}`"

    if [ -n "${LOCAL_CTL_FILE}" ]; then
        #
        # 対象のコントロールファイルが存在する場合、OK（何もしない）
        #
        :
    else

        if [ "${REAL_KBN}" = "2" ]; then
            #
            # 対象のコントロールファイルが存在しないが、メール配信ログは対象外のためスキップ（コントロールファイルがない場合がありえる）
            #
            :
        else
            #
            # 対象のコントロールファイルが存在しない場合、異常終了
            #
            echo "コントロールファイルが存在しません。ファイル名：${FIND_CTL_FILE}" 1>&2
            # 異常終了
            exit -1
        fi
    fi

done < ${LOCAL_BASEDIR}/work/rawdatalist_${TARGET_LOAD_GROUPID}.csv

# リストに従ってGCSへのコピーを実行
while read line
do

    # コメント行や空行をスキップ
    result=`echo ${line} | tr -d "\r" | tr -d "\n"`
    if [ `echo ${result} | egrep "^#" | wc -l` -gt 0 ] || [ "${result}" = "" ]; then
        continue
    fi

    # カンマでsplit
    set -- ${line}

    REAL_KBN=${1}
    LOAD_GROUPID=${2}
    PARALLEL_NO=${3}
    FROM_FILE_PATH=${4}
    FROM_FILE_NAME=${5}
    TO_FILE_NAME=${6}

    # 対象のロードグループID以外はスキップ
    if [ "${TARGET_LOAD_GROUPID}" != "${LOAD_GROUPID}" ]; then
        continue
    fi

    # GCS上の対象ディレクトリ
    GCS_DIRNAME=${LOAD_YMD}-${LOAD_GROUPID}

    # コピー先ファイル名
    GCS_FILE=${TO_FILE_NAME}.zip

    # コピー元ファイル名
    FIND_FILE="${FROM_FILE_NAME}_*.zip"
    LOCAL_FILE="`find ${FROM_FILE_PATH} -type f -name ${FIND_FILE}`"

    if [ -n "${LOCAL_FILE}" ]; then
        #
        # 対象ファイルが存在する場合
        #

        if [ "${REAL_KBN}" = "0" ]; then
            #
            # HBファイル用
            #

            # コピー実行
            gsutil cp ${LOCAL_FILE} gs://${DATA_BUCKET}/${GCS_DIRNAME}/${PARALLEL_NO}/${GCS_FILE}
            RETURN_CD=${?}
            if [ ${RETURN_CD} != 0 ]; then
                # 異常終了
                exit -1
            fi

        elif [ "${REAL_KBN}" = "1" ]; then
            #
            # リアル書店ファイル用
            #

            # 作業ディレクトリ削除
            rm -rf ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}

            # 作業ディレクトリ作成
            mkdir ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}

            # 解凍（対象ファイルが複数存在する可能性あり）
            unzip "${FROM_FILE_PATH}/${FIND_FILE}" -d ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/

            # リスト区切り文字
            IFS=$'\n'

            # zipを展開した各ファイルごとに編集処理を行う
            for targetfile in `\find "${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/" -maxdepth 1 -type f`; do

                # フッター除去
                sed -i "/@END/d" ${targetfile}

                # 行末にファイル名を付与
                filename=`basename ${targetfile}`
                sed -i "s/\$/|${filename}/g" ${targetfile}

            done

            # リスト区切り文字を戻す
            IFS=','

            # ファイル結合
            cat ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/* > ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.TSV

            # 対象ファイルの差替え
            LOCAL_FILE=${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.zip

            # 圧縮
            zip -j ${LOCAL_FILE} ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.TSV

            # コピー実行
            gsutil cp ${LOCAL_FILE} gs://${DATA_BUCKET}/${GCS_DIRNAME}/${PARALLEL_NO}/${GCS_FILE}
            RETURN_CD=${?}
            if [ ${RETURN_CD} != 0 ]; then
                # 異常終了
                exit -1
            fi

        elif [ "${REAL_KBN}" = "2" ]; then
            #
            # Cuenote配信ログ、開封ログ
            #

            # 作業ディレクトリ削除
            rm -rf ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}

            # 作業ディレクトリ作成
            mkdir ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}

            # 解凍（対象ファイルが複数存在する可能性あり）
            unzip "${FROM_FILE_PATH}/${FIND_FILE}" -d ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/

            # リスト区切り文字
            IFS=$'\n'

            # zipを展開した各ファイルごとに編集処理を行う
            for targetfile in `\find "${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/" -maxdepth 1 -type f`; do

                # 行末にファイル名を付与
                filename=`basename ${targetfile}`
                sed -i "s/\$/,${filename}/g" ${targetfile}

            done

            # リスト区切り文字を戻す
            IFS=','
            # ファイル結合
            cat ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/* > ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.csv

            # 対象ファイルの差替え
            LOCAL_FILE=${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.zip

            # 圧縮
            zip -j ${LOCAL_FILE} ${LOCAL_BASEDIR}/work/${FROM_FILE_NAME}/${FROM_FILE_NAME}.csv

            # コピー実行
            gsutil cp ${LOCAL_FILE} gs://${DATA_BUCKET}/${GCS_DIRNAME}/${PARALLEL_NO}/${GCS_FILE}
            RETURN_CD=${?}
            if [ ${RETURN_CD} != 0 ]; then
                # 異常終了
                exit -1
            fi

        fi

        # コピー元ファイル削除
        rm -f ${FROM_FILE_PATH}/${FIND_FILE}

        # コピー処理結果ログ出力
        echo "${LOCAL_FILE},${GCS_DIRNAME}/${GCS_FILE}" >> ${GCS_SEND_LOG}

    else
        #
        # 対象ファイルが存在しない場合、空ファイルを送信する
        #

        # 連携する空ファイル作成
        EMPTY_FILE=${LOCAL_BASEDIR}/${FROM_FILE_NAME}
        touch ${EMPTY_FILE}.csv
        zip -j ${EMPTY_FILE}.zip ${EMPTY_FILE}.csv

        # コピー実行
        gsutil cp ${EMPTY_FILE}.zip gs://${DATA_BUCKET}/${GCS_DIRNAME}/${PARALLEL_NO}/${GCS_FILE}
        RETURN_CD=${?}
        if [ ${RETURN_CD} != 0 ]; then
            # 異常終了
            exit -1
        fi

        # コピー処理結果ログ出力（空ファイル）
        echo "${FIND_FILE} (no file),${GCS_DIRNAME}/${GCS_FILE}" >> ${GCS_SEND_LOG}

        # 空ファイル削除
        rm ${EMPTY_FILE}.csv
        rm ${EMPTY_FILE}.zip

    fi

    # コントロールファイルを削除
    rm ${FROM_FILE_PATH}/${FROM_FILE_NAME}_*.ctl

done < ${LOCAL_BASEDIR}/work/rawdatalist_${TARGET_LOAD_GROUPID}.csv

# GCSへコピー処理結果ログをコピー
gsutil cp ${GCS_SEND_LOG} gs://${DATA_BUCKET}/log/
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

# オンプレミスサーバのコピー処理結果ログを削除
rm ${GCS_SEND_LOG}

# 正常終了
exit 0
