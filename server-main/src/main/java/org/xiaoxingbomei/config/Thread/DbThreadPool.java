package org.xiaoxingbomei.config.Thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class DbThreadPool
{
    // 用于落库操作的线程池
    private final ExecutorService dbThreadPool = new ThreadPoolExecutor
            (
            10, // 核心线程数
            20, // 最大线程数
            60L, // 空闲线程存活时间
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), // 队列容量
            new ThreadFactoryBuilder().setNamePrefix("DB-ThreadPool-").build(), // 自定义线程名称
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：调用线程执行任务
    );

    // 获取线程池
    public ExecutorService getThreadPool()
    {
        return dbThreadPool;
    }

    // 应用关闭时，释放线程池资源
    @PreDestroy
    public void shutdown()
    {
        dbThreadPool.shutdown();
    }
}
