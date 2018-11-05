package com.zhenxuan.tradeapi.schedule.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 7天之前的订单，按表里的tid，合并打余额，
 */
public class PushCashbackScheduleMain {

    private static final Logger logger = LoggerFactory.getLogger(PushCashbackScheduleMain.class);

    private static ApplicationContext ap =
            new ClassPathXmlApplicationContext("classpath*:spring/spring-schedule.xml");

    public static void main(String[] args) {

    }
}
