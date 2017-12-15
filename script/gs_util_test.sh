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

# コピー処理結果ログファイルを作成
GCS_DELETE_LOG=${LOCAL_BASEDIR}/log/GCS_DELETE_${TARGET_LOAD_GROUPID}_`date +%Y%m%d%H%M%S`.log
touch ${GCS_DELETE_LOG}

# フォルダリストを取得
GCS_INPUT_FOLDERS=`gsutil ls -d gs://${DATA_BUCKET}/input`
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

GCS_OUTPUT_FOLDERS=`gsutil ls -d gs://${DATA_BUCKET}/output`
RETURN_CD=${?}
if [ ${RETURN_CD} -ne 0 ]; then
    # 異常終了
    exit -1
fi

GCS_DELETE_FOLDERS=$(${GCS_INPUT_FOLDERS}, ${GCS_OUTPUT_FOLDERS})

for GCS_DELETE_FOLDER in ${GCS_DELETE_FOLDERS}; do
    echo ${GCS_DELETE_FOLDER}
done

# 削除対象リストを抽出して、削除対象フォルダを削除
for GCS_DELETE_FOLDER in ${GCS_DELETE_FOLDERS}; do
    # フォルダ名を取得
    GCS_DELETE_FOLDER_NAME=`basename ${GCS_DELETE_FOLDER}`
    GCS_FOLDER_CREATED_DATE=${GCS_DELETE_FOLDER_NAME%%_*}

    # 削除対象リストを絞り込む
    if [ $(expr ${GCS_FOLDER_CREATED_DATE} \<= ${TARGET_DATE}) -eq 1 ]; then
        # フォルダ削除を実行
        gsutil rm -rf ${GCS_DELETE_FOLDER}
        RETURN_CD=${?}
        if [ ${RETURN_CD} != 0 ]; then
            # 異常終了
            exit -1
        fi

        # ログ出力
        echo ${GCS_DELETE_FOLDER} >> ${GCS_DELETE_LOG}
    fi
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
