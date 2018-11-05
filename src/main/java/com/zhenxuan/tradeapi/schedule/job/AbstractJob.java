/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.schedule.job;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * 抽象定时Job
 *
 * @author tianyu07.
 * @date 16/7/4 20:40.
 */
public abstract class AbstractJob<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractJob.class);

    protected List<T> dataList;

    protected abstract void beforeJob(T data);
    protected abstract boolean doJob(T data);
    protected abstract void afterJob(T data);

    protected void init() {
    }

    protected void destroy() {
    }

    protected final void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 执行方法
     * @return 执行结果,成功或失败
     */
    public boolean execute() {
        try {
            init();
            if (dataList != null) {
                for (T data : dataList) {

                    beforeJob(data);

                    doJob(data);

                    afterJob(data);
                }
            }

            destroy();

            return true;

        } catch (Exception e) {
            logger.error("AbstractJob execute error. due to {}", Throwables.getStackTraceAsString(e));
        }

        return false;
    }
}
