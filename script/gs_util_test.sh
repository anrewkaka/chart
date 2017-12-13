#!/bin/bash

# 引数チェック
if [ $# -ne 2 ]; then
    echo "引数が不正です。引数の個数：$#" 2>&2
    # 異常終了
    exit -1
fi

# ロードグループIDを取得
TARGET_LOAD_GROUPID=${1}

# 削除対象日を取得
TARGET_DATE=${2}

# 設定ファイル
. $(cd $(dirname $0); pwd)/gcp_dwh.env

# アカウント設定
gcloud auth activate-service-account ${SERVICE_ACCOUNT} \
    --key-file /opt/scripts/classes/${KEY_FILE} --project ${PROJECT_NAME}

# プロジェクト設定
gcloud config set project ${PROJECT_NAME}

# コピー処理結果ログファイル作成
GCS_DELETE_LOG=${LOCAL_BASEDIR}/log/GCS_DELETE_${TARGET_LOAD_GROUPID}_`date +%Y%m%d%H%M%S`.log
touch ${GCS_DELETE_LOG}

# 削除対象リストを取得
GCS_DELETE_FOLDERS=`gsutil ls -d gs://${DATA_BUCKET}/delete_folder/*${TARGET_LOAD_GROUPID}`
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

# for GCS_DELETE_FOLDER in `gsutil ls -d gs://${DATA_BUCKET}/delete_folder/*${TARGET_LOAD_GROUPID}`; do
for GCS_DELETE_FOLDER in ${GCS_DELETE_FOLDERS}; do
    GCS_FOLDER_NAME = `basename ${GCS_DELETE_FOLDER}`
    GCS_FOLDER_CREATED_DATE=`$(cut -d'-' -f1 <<<${GCS_FOLDER_NAME})`
    echo ${GCS_FOLDER_CREATED_DATE}
done


# GCSへコピー処理結果ログをコピー
gsutil cp ${GCS_DELETE_LOG} gs://${DATA_BUCKET}/log/
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

# オンプレミスサーバのコピー処理結果ログを削除
rm ${GCS_DELETE_LOG}

# 正常終了
exit 0
