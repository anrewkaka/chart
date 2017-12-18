#!/bin/sh

#
# 引数設定
#
MCNT=$1
if [ "x$MCNT" = "x" ] ; then
  # 未指定の場合：2（月）とする
  MCNT=2
fi

#
# 対象日設定(YYYYMMDD)
#
TARGET_DATE=`date --date="${MCNT} months ago" +%Y%m%d`

#
# 環境設定ファイル
#
. $(cd $(dirname $0); pwd)/gcp_dwh.env

#
# 現在日時
#
CURRENT_TIMESTAMP=`date +%Y%m%d%H%M%S`

#
# ログ出力先ファイルパス
#
GCS_SEND_LOG=${LOCAL_BASEDIR}/log/ETC_BAT_MAIL_GCS_DELETE_${CURRENT_TIMESTAMP}.log
touch ${GCS_SEND_LOG}

#
# GCPアカウント認証
#
gcloud auth activate-service-account ${SERVICE_ACCOUNT} \
    --key-file /opt/scripts/classes/${KEY_FILE} --project ${PROJECT_NAME}
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # ログ出力
    echo "`date '+%T'` GCSへの接続に失敗しました" | tee -a ${GCS_SEND_LOG}
    # 異常終了
    exit 1
fi

#
# プロジェクト設定
#
gcloud config set project ${PROJECT_NAME}

#
# ディレクトリリストを削除
#
delete_by_path() {
    GCS_TARGET_DIRECTORY=$1
    GCS_TARGET_PATH=`echo gs://${DATA_BUCKET}/${GCS_TARGET_DIRECTORY}`
    GCS_DELETE_DIRECTORIES=`gsutil ls ${GCS_TARGET_PATH}/**`
    if [ ${RETURN_CD} -ne 0 ]; then
        # 異常終了
        exit 1
    fi

echo $(expr ${GCS_DELETE_DIRECTORIES} == ${GCS_TARGET_PATH})

exit 0

    if [ $(expr ${GCS_DELETE_DIRECTORIES} \== ${GCS_TARGET_PATH}) -eq 0 ]; then
        # ログ出力
        "`date '+%T'` 空のディレクトリ：" ${GCS_TARGET_PATH} | tee -a ${GCS_SEND_LOG}
    fi

    for GCS_DELETE_DIRECTORY in ${GCS_DELETE_DIRECTORIES}; do
        # フォルダ名を取得
        GCS_DELETE_DIRECTORY_NAME=`basename ${GCS_DELETE_DIRECTORY}`
        GCS_DIRECTORY_CREATED_DATE=${GCS_DELETE_DIRECTORY_NAME%%_*}

        # 削除対象リストを絞り込む
        if [ $(expr ${GCS_DIRECTORY_CREATED_DATE} \<= ${TARGET_DATE}) -eq 1 ]; then
            # フォルダ削除を実行
            gsutil rm -rf ${GCS_DELETE_DIRECTORY}
            RETURN_CD=${?}
            if [ ${RETURN_CD} != 0 ]; then
                # ログ出力
                echo "`date '+%T'` ディレクトリ削除失敗：" ${GCS_DELETE_DIRECTORY} | tee -a ${GCS_SEND_LOG}
                # 異常終了
                exit 1
            fi

            # ログ出力
            echo "`date '+%T'` ディレクトリ削除成功：" ${GCS_DELETE_DIRECTORY} | tee -a ${GCS_SEND_LOG}
        fi
    done
}

# 【input】のディレクトリを削除
delete_by_path "input"

# 【output】のディレクトリを削除
delete_by_path "output"

# 正常終了
exit 0
