#!/bin/bash

#建议每天上午0点1分执行
#1 0 * * * sh pushCashbackJob.sh

export LANG=en_US.utf8

java -cp "conf/*:lib/*:trade-api.jar" com.zhenxuan.tradeapi.schedule.job.PushCashbackJob
