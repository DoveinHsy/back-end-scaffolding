package org.xiaoxingbomei.config.Thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ExternalServiceThreadPool
{
    // 用于调用外部服务的线程池
    private final ExecutorService externalServiceThreadPool = new ThreadPoolExecutor(
            20, // 核心线程数
            50, // 最大线程数
            60L, // 空闲线程存活时间
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500), // 队列容量
            new ThreadFactoryBuilder().setNamePrefix("ExternalService-ThreadPool-").build(), // 自定义线程名称
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略 CallerRunsPolicy 避免任务丢失
    );

    // 获取线程池
    public ExecutorService getThreadPool()
    {
        return externalServiceThreadPool;
    }

    // 应用关闭时，释放线程池资源
    @PreDestroy
    public void shutdown()
    {
        externalServiceThreadPool.shutdown();
    }
}
