#!/bin/bash

# 应用启动命令
APP_START_COMMAND="java -jar ./ecmp-customer-mock-1.0-SNAPSHOT.jar --server.port=9090"

# 日志文件路径
LOG_FILE_PATH="./log.txt"

# 后台运行应用并将日志输出到文件
$APP_START_COMMAND > $LOG_FILE_PATH 2>&1 &

# 获取应用进程ID
APP_PID=$!

echo $APP_PID >> pid.txt

echo "Mock服务已成功启动，进程ID: $APP_PID"