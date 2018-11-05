//package com.zhenxuan.tradeapi.schedule.main;
//
//import com.baidu.fbu.settlement.schedule.pushresult.CollectionJob;
//import com.baidu.fbu.settlement.schedule.pushresult.PaymentJob;
//import com.baidu.fbu.settlement.schedule.pushresult.RefundJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * 推动任务定时调度类
// *
// * @anthor heyijie
// * @date 16/7/6
// */
//public class PushJobScheduleMain {
//    private static Logger logger = LoggerFactory.getLogger(PushJobScheduleMain.class);
//    private static ApplicationContext ap =
//            new ClassPathXmlApplicationContext("classpath*:spring/settlement.schedule.context.xml");
//
//    public static void main(String[] args) {
//        collectionPushJob();
//        paymentPushJob();
//        refundPushjob();
//    }
//
//    /**
//     * 退款回调
//     */
//    public static void refundPushjob() {
//        RefundJob refundJob = ap.getBean("refundJob", RefundJob.class);
//        if (!refundJob.execute()) {
//            logger.error("fail to schedule refund push job!");
//        }
//    }
//
//    /**
//     * 转账回调
//     */
//    public static void paymentPushJob() {
//        PaymentJob paymentJob = ap.getBean("paymentJob", PaymentJob.class);
//        if (!paymentJob.execute()) {
//            logger.error("fail to schedule payment push job!");
//        }
//    }
//
//    /**
//     * 支付回调
//     */
//    public static void collectionPushJob() {
//        CollectionJob collectionJob = ap.getBean("collectionJob", CollectionJob.class);
//        if (!collectionJob.execute()) {
//            logger.error("fail to schedule collection push job!");
//        }
//    }
//}
