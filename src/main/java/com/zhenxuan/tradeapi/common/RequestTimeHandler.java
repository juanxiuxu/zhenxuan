/*
 * ©1999-2018 Neptune, Inc. All rights reserved.
 *
 * http://www.neptune.com
 *
 */

package com.zhenxuan.tradeapi.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 接口的请求影响时间需要监控, 但是目前平台没有找到合适的访问次数和访问时间监控.<p>
 * 每个接口都写请求时间参数太麻烦了. 直接统一监控时间.<p>
 *
 * 这个类只能监控到开始代理到代理结束时间段内程序的运行时间, 多线程环境中的监控时间跟发起请求到收到响应的时间肯定不一致!<p>
 * 考虑到请求传递, Tomcat接收, Spring通知, Spring后处理 等在多线程环境中的时间占用, 实际监控到的时间会小于请求发起者看到的时间.
 *
 * @author yupeng.qin
 * @since 2016-05-23
 */
@Aspect
@Component
public class RequestTimeHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestTimeHandler.class);

//    在使用ThreadLocal之前采取了如下思路:
//        选取线程安全的, 支持LRU的Map容纳当前JoinPoint的hashCode和时间戳. 异常时使用hashCode获取时间戳计算时长.
//        发现异常情况可能不多, 使用LRU还是会把内存撑爆（如果可能）.
//          使用LinkedHashMap线程不安全, 获取的值不可预期.
//          使用CurrentHashMap不支持LRU
//          如上两者都不支持最大容量.
//        因此换用ArrayBlockingQueue+CurrentHashMap的组合, 无需实现LRU.
//        发现取出的值无论如何都有误. 打印hashCode才发现原来工程采用的是spring singleton.
//        于是采用ThreadLocal的方式.
//
//        监控时间在线程数为CUP核心数3~4倍的情况下将会使得总计耗时差错 200%.
    private final ThreadLocal<Long> local = new ThreadLocal();

    /*StopWatch在该案例上的使用不佳, 直接使用当前系统时间 */
    @Around("execution(public * com.zhenxuan.fbu.fmis.fcore.controller.*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        long begin = System.currentTimeMillis();
        local.set(begin);

        Object o = jp.proceed();

        Signature sign = jp.getSignature();
        logger.info("执行{}, 耗时:{}.", sign.toShortString(), (System.currentTimeMillis() - begin));

        return o;
    }

    @AfterThrowing("execution(public * com.zhenxuan.fbu.fmis.fcore.controller.*.*(..))")
    public void afterThrowing(JoinPoint jp) throws Throwable {

        Signature sign = jp.getSignature();
        logger.info("执行{}, 出现异常, 耗时:{}.", sign.toShortString(), (System.currentTimeMillis() - local.get()));
    }

}
