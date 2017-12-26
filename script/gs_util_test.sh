#!/bin/bash

#
# 環境設定ファイル
#
. $(cd $(dirname $0); pwd)/gcp_mail.env

#
# 現在日時
#
CURRENT_TIMESTAMP=`date +%Y%m%d%H%M%S`

#
# ログ出力先ファイルパス
#
GCS_UNNECESSARY_DELETION_LOG=${LOCAL_BASEDIR}/log/ETC_BAT_MAIL_GCS_DELETE_UNNECESSARY_${CURRENT_TIMESTAMP}.log
touch ${GCS_UNNECESSARY_DELETION_LOG}

#
# 引数設定
#
MCNT=$1
if [ "x$MCNT" = "x" ]; then
    # 未指定の場合：2（月）とする
    MCNT=2
fi

# 引数の値が2より小さい場合、処理を中止する
if [ $(expr "$MCNT" : '^[0-9]$') -eq 0 ] || [ $MCNT -lt 2 ]; then
    # ログ出力
    echo "`date '+%T'` 不正な引数：${MCNT}（正しくは数値が2以上です。）" | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
    # 異常終了
    exit 1
fi

#
# 対象日設定(YYYYMMDD)
#
TARGET_DATE=`date --date="${MCNT} months ago" +%Y%m%d`

# GCPアカウント認証
gcloud auth activate-service-account ${SERVICE_ACCOUNT} \
    --key-file /opt/scripts/classes/${KEY_FILE} --project ${PROJECT_NAME}
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # ログ出力
    echo "`date '+%T'` GCSへの接続に失敗しました" | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
    # 異常終了
    exit 1
fi

# プロジェクト設定
gcloud config set project ${PROJECT_NAME}

#
# 対象ディレクトリを削除する
#
# 【引数】
#  第1引数 GCS_TARGET_DIRECTORY: 対象ディレクトリ(intput/output)
#
delete_by_path() {
    GCS_TARGET_DIRECTORY=$1
    GCS_TARGET_PATH="gs://${DATA_BUCKET}/${GCS_TARGET_DIRECTORY}/"
    # ディレクトリパスリストを取得する
    GCS_DELETION_DIRECTORIES=`gsutil ls ${GCS_TARGET_PATH}`
    RETURN_CD=${?}
    if [ ${RETURN_CD} -ne 0 ]; then
        # ログ出力
        echo "`date '+%T'` GCSからのディレクトリリスト取得に失敗しました" | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
        # 異常終了
        exit 1
    fi

    # ディレクトリパスリストが空リストの場合、ログ出力する
    if [ "$GCS_DELETION_DIRECTORIES" = "$GCS_TARGET_PATH" ]; then
        # ログ出力
        echo "`date '+%T'` 空のディレクトリ: " ${GCS_TARGET_PATH} | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
    fi

    for GCS_DELETION_DIRECTORY in ${GCS_DELETION_DIRECTORIES}; do
        # ディレクトリ名を取得する
        GCS_DELETION_DIRECTORY_NAME=`basename ${GCS_DELETION_DIRECTORY}`
        # 作成日を抽出する
        GCS_DIRECTORY_CREATED_DATE=${GCS_DELETION_DIRECTORY_NAME%%-*}

        # 抽出した文字列のフォーマットが「YYYYMMDD」以外の場合、次の要素の処理を実施する
        if [ $(expr "$GCS_DIRECTORY_CREATED_DATE" : '^[0-9]\{8\}$') -eq 0 ]; then
            continue
        fi

        # 作成日が対象日より前の場合、ディレクトリを削除する
        if [ $(expr ${GCS_DIRECTORY_CREATED_DATE} \< ${TARGET_DATE}) -eq 1 ]; then
            # ディレクトリ削除を実行する
            gsutil rm -rf ${GCS_DELETION_DIRECTORY}
            RETURN_CD=${?}
            if [ ${RETURN_CD} != 0 ]; then
                # ログ出力
                echo "`date '+%T'` ディレクトリ削除失敗: " ${GCS_DELETION_DIRECTORY} | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
                continue
            fi

            # ログ出力
            echo "`date '+%T'` ディレクトリ削除成功: " ${GCS_DELETION_DIRECTORY} | tee -a ${GCS_UNNECESSARY_DELETION_LOG}
        fi
    done
}

# 【input】のディレクトリを削除する
delete_by_path "input"

# 【output】のディレクトリを削除する
delete_by_path "output"

# 正常終了
exit 0
