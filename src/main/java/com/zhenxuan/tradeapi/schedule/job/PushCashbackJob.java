package com.zhenxuan.tradeapi.schedule.job;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.common.enums.BalanceStatus;
import com.zhenxuan.tradeapi.common.enums.OrderStatus;
import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import com.zhenxuan.tradeapi.dao.entity.UserBalanceBillEntity;
import com.zhenxuan.tradeapi.dao.mapper.OrderMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserBalanceBillMapper;
import com.zhenxuan.tradeapi.utils.DateUtil;
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
 * 7天之前的订单，按表里的tid，合并打余额，
 */
@Service
public class PushCashbackJob extends BaseJob {

    private static final Logger logger = LoggerFactory.getLogger(PushCashbackJob.class);

    @Value("${job.pushcashback.range.day}")
    private long rangeDay;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserBalanceBillMapper userBalanceBillMapper;

    public static void main(String[] args) {
        execute(args, PushCashbackJob.class);
    }

    @Override
    public void doExecute(String[] args) {
        int orderStatus = OrderStatus.PAY_SUCCESS.code;
        int balanceStatus = BalanceStatus.UNKNOWN.code;
//        long curTime = System.currentTimeMillis() / 1000;
//        long endPaidAt = curTime - rangeDay * 24 * 3600;
//        long startPaidAt = endPaidAt - 24 * 3600;
        long endPaidAt = 1553426184;
        long startPaidAt = 1541436184;
        List<OrderEntity> orderEntities = orderMapper.selectEntitiesByPaidAtRangeAndStatus(
                orderStatus, balanceStatus, startPaidAt, endPaidAt);

        if (orderEntities != null) {
            for (OrderEntity orderEntity : orderEntities) {
                long cashback = orderEntity.getCashback();
                if (cashback == 0) {
                    continue;
                }

                String orderId = orderEntity.getOid();
                String uid = orderEntity.getAuthUid();

                try {
                    pushCashback(uid, cashback, orderId, orderEntity);
                } catch (Exception e) {
                    logger.error("push cashback is failed. uid:[{}], orderId:[{}], cashback:[{}]. err:{}",
                            uid, orderId, cashback, Throwables.getStackTraceAsString(e));
                    continue;
                }
            }
        }
    }

    @Override
    public void preExecute(String[] args) {
        super.preExecute(args);
        logger.info("PushCashbackJob begin");
    }

    @Override
    public void afterExecute() {
        super.afterExecute();
        logger.info("PushCashbackJob end");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void pushCashback(String uid, long cashback, String orderId, OrderEntity orderEntity) {
        userAuthMapper.increaseUserBalance(uid, cashback);

        UserBalanceBillEntity billEntity = new UserBalanceBillEntity();
        billEntity.setBillId(GlobalIdUtil.newOrderId());
        billEntity.setOid(orderId);
        billEntity.setAuthUid(uid);
        billEntity.setAmount(cashback);
        billEntity.setIncome(1);
        billEntity.setCompleteStatus(1); // 1 or 0
        billEntity.setBalanceStatus(1); // 1 or 0
        billEntity.setCancelStatus(0); // 0
        billEntity.setCompletedAt(DateUtil.convertTimestamp2Date(orderEntity.getPaidAt()));
        billEntity.setBillDesc("订单分润");
        billEntity.setBillType(1); // TODO
        userBalanceBillMapper.insertEntity(billEntity);

        int oldStatus = BalanceStatus.UNKNOWN.code;
        int newStatus = BalanceStatus.CASHBACK_PUSHED.code;
        int upResult = orderMapper.updateBalanceStatus(orderId, oldStatus, newStatus);
        if (upResult == 0) {
            logger.error("no order to update balance status. orderId:{}", orderId);
            throw new RuntimeException("no order to update balance status");
        }
    }
}
