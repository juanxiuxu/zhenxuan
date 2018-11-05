package com.zhenxuan.tradeapi.schedule.job;

import com.zhenxuan.tradeapi.common.enums.OrderStatus;
import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import com.zhenxuan.tradeapi.dao.entity.UserBalanceBillEntity;
import com.zhenxuan.tradeapi.dao.mapper.OrderMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserBalanceBillMapper;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 7天前的cashback打余额
 */
@Service
public class PushCashbackJob extends AbstractJob<OrderEntity> {

    private static final Logger logger = LoggerFactory.getLogger(PushCashbackJob.class);

    @Value("${job.pushcashback.range.day}")
    private long rangeDay;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserBalanceBillMapper userBalanceBillMapper;

    @Override
    protected void init() {
        super.init();
        logger.info("PushCashbackJob begin");

        int orderStatus = OrderStatus.PAY_SUCCESS.code;
        long endPaidAt = rangeDay * 24 * 3600;
        long startPaidAt = endPaidAt + 24 * 3600;
        List<OrderEntity> orderEntities = orderMapper.selectEntitiesByPaidAtRangeAndStatus(orderStatus, startPaidAt, endPaidAt);
        setDataList(orderEntities);
    }

    @Override
    protected void destroy() {
        super.destroy();
        logger.info("PushCashbackJob end");
    }

    @Override
    protected void beforeJob(OrderEntity data) {
    }

    @Override
    protected boolean doJob(OrderEntity data) {
        String orderId = data.getOid();
        long cashback = data.getCashback();
        String uid = data.getAuthUid();

        try {
            pushCashback(uid, cashback, orderId);
        } catch (Exception e) {
            logger.error("push cashback is failed. uid:[{}], orderId:[{}], cashback:[{}]",
                    uid, orderId, cashback);
            return false;
        }

        return true;
    }

    @Override
    protected void afterJob(OrderEntity data) {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void pushCashback(String uid, long cashback, String orderId) {
        userAuthMapper.increaseUserBalance(uid, cashback);

        UserBalanceBillEntity billEntity = new UserBalanceBillEntity();
        billEntity.setBillId(GlobalIdUtil.newOrderId());
        billEntity.setOrderId(orderId);
        billEntity.setAuthUid(uid);
        billEntity.setAmount(cashback);
        billEntity.setIncome(1);
        // TODO
        userBalanceBillMapper.insertEntity(billEntity);

        int oldOrderStatus = OrderStatus.PAY_SUCCESS.code;
        int newOrderStatus = OrderStatus.PUSH_CASHBACK_FINISH.code;
        int upResult = orderMapper.updateOrderState(orderId, oldOrderStatus, newOrderStatus, System.currentTimeMillis() / 1000);
        if (upResult == 0) {
            logger.error("no order to update status. orderId:{}", orderId);
            throw new RuntimeException("no order to update status");
        }
    }
}
