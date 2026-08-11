#!/bin/sh
set -e

echo "[liquibase-runner] Check pending changeset"
liquibase --defaults-file=/liquibase/liquibase.properties status --verbose

echo "[liquibase-runner] Create backup snap-shot"
liquibase --defaults-file=/liquibase/liquibase.properties snapshot --output-file=backup.json --snapshot-format=json

echo "[liquibase-runner] Start migration"
if liquibase --defaults-file=/liquibase/liquibase.properties update; then
    echo "[liquibase-runner] Migration success"
    exit 0
else
    echo "[liquibase-runner] Migration failed - Require manual review"
    echo "[liquibase-runner] Create review snapshot"
    liquibase --defaults-file=/liquibase/liquibase.properties snapshot --output-file=review.json --snapshot-format=json
    exit 1
fi