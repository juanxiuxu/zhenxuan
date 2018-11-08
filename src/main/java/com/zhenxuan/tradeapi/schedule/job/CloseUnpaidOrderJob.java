package com.zhenxuan.tradeapi.schedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时取消订单
 */
public class CloseUnpaidOrderJob extends BaseJob {

    private static final Logger logger = LoggerFactory.getLogger(CloseUnpaidOrderJob.class);

    @Override
    public void doExecute(String[] args) {

    }
}
