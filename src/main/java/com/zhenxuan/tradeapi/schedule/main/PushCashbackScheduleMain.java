//package com.zhenxuan.tradeapi.schedule.main;
//
//import com.zhenxuan.tradeapi.schedule.factory.BeanFactory;
//import com.zhenxuan.tradeapi.schedule.job.PushCashbackJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 7天之前的订单，按表里的tid，合并打余额，
// */
//public class PushCashbackScheduleMain {
//
//    private static final Logger logger = LoggerFactory.getLogger(PushCashbackScheduleMain.class);
//
//    public static void main(String[] args) {
//        BeanFactory beanFactory = BeanFactory.create();
//
//        PushCashbackJob pushCashbackJob = beanFactory.getPushCashbackJob();
//        if (!pushCashbackJob.execute()) {
//            logger.error("Fail to schedule PushCashback job.");
//        }
//    }
//}
