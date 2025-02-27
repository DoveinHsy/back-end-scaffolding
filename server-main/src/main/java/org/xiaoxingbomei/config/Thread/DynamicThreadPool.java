package org.xiaoxingbomei.config.Thread;

import lombok.extern.slf4j.Slf4j;
import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
import org.xiaoxingbomei.entity.TaskWithTimestamp;
import org.xiaoxingbomei.entity.ThreadPoolMonitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动态线程池实现，继承自ThreadPoolExecutor。
 * 具备动态调整核心线程数和最大线程数的能力。
 */
@Slf4j
public class DynamicThreadPool extends ThreadPoolExecutor
{

    private long inQueueTime; // 记录任务入队时间

    // ======================================================================================================


    /**
     * 构造函数，用于初始化线程池。
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   非核心线程空闲存活时间
     * @param unit            时间单位
     * @param workQueue       任务队列
     */
    public DynamicThreadPool
    (
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue
    )
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 动态设置核心线程数。
     *
     * @param corePoolSize 新的核心线程数
     */
    public void updateCorePoolSize(int corePoolSize)
    {
        setCorePoolSize(corePoolSize);
        log.info("\t核心线程数已更新为: {}", corePoolSize);
    }

    /**
     * 动态设置最大线程数。
     *
     * @param maximumPoolSize 新的最大线程数
     */
    public void updateMaximumPoolSize(int maximumPoolSize)
    {
        setMaximumPoolSize(maximumPoolSize);
        log.info("\t最大线程数已更新为: {}", maximumPoolSize);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r)
    {
        // 在任务执行之前进行一些监控处理
        log.info("Thread {} will start executing task {}", t.getName(), r.getClass().getSimpleName());
        // 记录任务入队的时间
        if (r instanceof TaskWithTimestamp)
        {
            inQueueTime = ((TaskWithTimestamp) r).getInQueueTime();
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t)
    {
        // 在任务执行之后进行一些监控处理
        // if (t == null) {
        //     log.info("Task {} executed successfully", r.getClass().getSimpleName());
        // } else {
        //     log.error("Task {} failed with exception", r.getClass().getSimpleName(), t);
        // }
        long taskWaitTime = System.currentTimeMillis() - inQueueTime; // 计算任务阻塞时间
        // 收集监控数据
        collectAndLogMonitorData(taskWaitTime);
        super.afterExecute(r, t);
    }

    private void collectAndLogMonitorData(long taskWaitTime) {
        // 获取当前线程池状态
        ThreadPoolMonitor monitorData = new ThreadPoolMonitor();
        monitorData.setActiveCount(getActiveCount());
        monitorData.setMaxPoolSize(getMaximumPoolSize());
        monitorData.setQueueSize(getQueue().size());
        monitorData.setQueueCapacity(((DynamicLinkedBlockingQueue) getQueue()).getCapacity());
        monitorData.setTaskWaitTime(taskWaitTime);

        // 打印日志输出监控信息
        log.info("ThreadPool Monitor Data: {}", monitorData);
    }

    @Override
    protected void terminated()
    {
        // 线程池被终止时的处理逻辑
        log.info("Dynamic thread pool has been terminated.");
    }


    /**
     * 打印线程池当前状态，便于监控。
     */
    public void printThreadPoolStatus()
    {
        log.info
                (         "\t\n【线程池状态】：\n"
                        + "\t核心线程数: {}\n"
                        + "\t最大线程数: {}\n"
                        + "\t当前活动线程数: {}\n"
                        + "\t任务队列大小: {}\n",
                getCorePoolSize(),
                getMaximumPoolSize(),
                getActiveCount(),
                getQueue().size()
                );
    }

    /**
     * 获取线程池当前状态
     */
    public String getThreadPoolStatus()
    {
        String threadPoolStatus = "线程池状态===>" +
                "isShutdown:【"+isShutdown()+"】" +
                "isTerminated:【"+isTerminated()+"】" +
                "isTerminating:【"+isTerminating()+"】" +
                "核心线程数:【"+getCorePoolSize()+"】" +
                "最大线程数:【"+getMaximumPoolSize()+"】" +
                "当前活动线程数:【"+getActiveCount()+"】" +
                "任务队列大小:【"+getQueue().size()+"】";
        return threadPoolStatus;

    }



}