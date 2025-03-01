package org.xiaoxingbomei.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.config.Thread.DynamicThreadPool;
import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
import org.xiaoxingbomei.entity.ThreadPoolMonitor;

/**
 * 线程池监控任务
 */
@Component
@Slf4j
public class ThreadPoolMonitorTask
{

    private final DynamicThreadPool dynamicThreadPool;

    public ThreadPoolMonitorTask(DynamicThreadPool dynamicThreadPool)
    {
        this.dynamicThreadPool = dynamicThreadPool;
    }

    @Scheduled(fixedRate = 100000) // 每 10 秒钟上报一次
    public void reportThreadPoolStatus()
    {
        // 获取当前线程池状态
        ThreadPoolMonitor monitorData = new ThreadPoolMonitor();
        monitorData.setActiveCount(dynamicThreadPool.getActiveCount());
        monitorData.setMaxPoolSize(dynamicThreadPool.getMaximumPoolSize());
        monitorData.setQueueSize(dynamicThreadPool.getQueue().size());
        monitorData.setQueueCapacity(((DynamicLinkedBlockingQueue) dynamicThreadPool.getQueue()).getCapacity());

        // 这里将监控数据上报到 Kafka 或数据库等
        log.info("Scheduled ThreadPool Monitor Data: {}", monitorData);

        // sendToKafka(monitorData);
    }
}
