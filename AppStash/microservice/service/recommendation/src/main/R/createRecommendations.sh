#!/bin/sh
export RAW_FILE=/tmp/export.csv
export NORMALIZED_RAW_FILE=/tmp/user-item-pref.csv
export TARGET_DIR=/usr/share/shop/recommendation/output/api/recommendation/
export R_SCRIPT=/usr/share/shop/recommendation/recommendation.R

echo "Recommendation-Generation started:  $(date)"
rm "$RAW_FILE" "$NORMALIZED_RAW_FILE"
mongoexport -h mongodb-node -d shop -c recommendation.preference --csv --fields userId,articleId,preference --out "$RAW_FILE"
cat /tmp/export.csv | sed 's/ObjectID(//' | sed 's/),"/,/' | sed 's/",/,/' > "$NORMALIZED_RAW_FILE"
mkdir -p "$TARGET_DIR"
Rscript "$R_SCRIPT" "$NORMALIZED_RAW_FILE" "$TARGET_DIR/all"
echo "Recommendation-Generation finished:  $(date)"
