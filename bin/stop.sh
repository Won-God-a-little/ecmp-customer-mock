#!/bin/bash

# 应用停止命令,依赖pid.txt文件
tail -n 1 pid.txt | xargs kill -9