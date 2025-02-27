package org.xiaoxingbomei.config.shceduled;


import java.util.concurrent.ScheduledFuture;

/**
 * ScheduledFuture包装类
 * 1、取消定时任务
 */
public final class ScheduledTaskConfig
{

    // ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果
    volatile ScheduledFuture<?> future;


    /**
     * 取消定时任务
     */
    public void cancel()
    {
        ScheduledFuture<?> future = this.future;
        if (future != null)
        {
            future.cancel(true);
        }
    }
}
