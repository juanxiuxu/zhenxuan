package com.zhenxuan.tradeapi.schedule.job;

import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时取消订单
 */
public class CancelOrderJob extends AbstractJob<OrderEntity> {

    private static final Logger logger = LoggerFactory.getLogger(CancelOrderJob.class);

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void destroy() {
        super.destroy();
    }

    @Override
    protected void beforeJob(OrderEntity data) {

    }

    @Override
    protected boolean doJob(OrderEntity data) {
        return false;
    }

    @Override
    protected void afterJob(OrderEntity data) {

    }
}
