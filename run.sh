#!/usr/bin/env bash
# CEM システムをローカルで起動する（macOS / Linux）。
# 前提: JDK 21、Maven 3.9+、Oracle DB（接続先は src/main/resources/application.properties）。
set -euo pipefail
cd "$(dirname "$0")"

# application.properties のログ出力先は Windows 用（C:/logs/...）なので、ローカルのパスで上書きする
export LOG_DIR_APP="${LOG_DIR_APP:-$PWD/logs/app.log}"
export LOG_DIR_JDBC="${LOG_DIR_JDBC:-$PWD/logs/JDBC.log}"
mkdir -p "$(dirname "$LOG_DIR_APP")" "$(dirname "$LOG_DIR_JDBC")"

# Tailwind CSS をビルドする（Node.js がある場合。ビルド済みの app.css はコミットしている）
if [ -x node_modules/.bin/tailwindcss ]; then
    npm run --silent css:build
fi

MVN="${MVN:-mvn}"
exec "$MVN" spring-boot:run
