package com.zhenxuan.tradeapi.schedule.job;

import com.zhenxuan.tradeapi.schedule.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * desc：定时任务的基类，统一集成此基类
 * auther: wupeng04
 * date: 2017/2/27
 */
public abstract class BaseJob {

    protected static final Logger logger = LoggerFactory.getLogger(BaseJob.class);

    public abstract void doExecute(String[] args) throws Exception;

    public void preExecute(String[] args) {

    }

    public void afterExecute() {
        lazyDestroy(getDelayExit());
    }

    /**
     * 在spring启动context之后，如果是在tomcat容器中，tomcat关闭时会调用context.close方法，但是如果在main方法启动之后，
     * 需要手动调用context.close方法才能触发数据库连接(destroyMethod=close)这样的方法。不然会导致java进程结束后和db的连接异常中断
     */
    public static void execute(String[] args, Class clazz) {
        AnnotationConfigApplicationContext context = null;
        BaseJob task = null;
        boolean isSuccess = false;

        try {
//            context = new AnnotationConfigApplicationContext(ScheduleApplicationContext.class);
//            task = (BaseJob) context.getBean(clazz);
            task = BeanFactory.getJobBean(clazz);
            task.preExecute(args);
            task.doExecute(args);
            isSuccess = true;
        } catch (Exception e) {
            logger.error("BaseJob#execute exception:", e);
        } finally {
            if (task != null) {
                task.afterExecute();
            }
            if (context != null) {
                context.close();
            }
        }

        if (isSuccess) {
            System.exit(0);
        }

        System.exit(-1);
    }

    // 延时退出时间，防止异步事件被拒绝
    protected long getDelayExit() {
        return 0;
    }

    public void lazyDestroy(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            logger.error("AmsBaseTask#lazyDestroy exception:", e);
        }
    }
}
