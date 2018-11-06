package com.zhenxuan.tradeapi.schedule.factory;

import com.zhenxuan.tradeapi.schedule.job.BaseJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactory {

    private static ApplicationContext ap =
            new ClassPathXmlApplicationContext("classpath*:spring/spring-schedule.xml");

//    public static BeanFactory create() {
//        return new BeanFactory();
//    }
//
//    private BeanFactory() {
//    }
//
//    public PushCashbackJob getPushCashbackJob() {
//        return ap.getBean("pushCashbackJob", PushCashbackJob.class);
//    }
//
//    public CloseUnpaidOrderJob getCancelOrderJob() {
//        return ap.getBean("cancelOrderJob", CloseUnpaidOrderJob.class);
//    }

    public static <T extends BaseJob> T getJobBean(Class<T> clazz) {
        return ap.getBean(clazz);
    }
}
