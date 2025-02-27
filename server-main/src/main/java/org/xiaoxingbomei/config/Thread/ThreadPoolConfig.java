package org.xiaoxingbomei.config.Thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;


import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class ThreadPoolConfig
{

    @Value("${apoolo.dynamicthreadpool.corePoolSize}")
    public String corePoolSize;     // 核心线程数

    @Value("${apoolo.dynamicthreadpool.maximumPoolSize}")
    public String maximumPoolSize;  // 最大线程数

    @Value("${apoolo.dynamicthreadpool.keepAliveTime}")
    public String keepAliveTime;    // 空闲线程存活时间

    @Value("${apoolo.dynamicthreadpool.workQueueCapacity}")
    public String workQueueCapacity;// 空闲线程存活时间

    // Apollo 配置对象
    private Config apolloConfig;

    // 动态线程池实例
    private DynamicThreadPool_bak dynamicThreadPool;

    // 在初始化时获取 Apollo 配置，并监听配置变更
    public ThreadPoolConfig()
    {
        apolloConfig = ConfigService.getAppConfig(); // 获取 Apollo 配置
        loadThreadPoolConfig();  // 加载初始线程池配置
    }

    // 初始化线程池配置
    private void loadThreadPoolConfig()
    {
        this.corePoolSize = apolloConfig.getProperty("apoolo.dynamicthreadpool.corePoolSize", corePoolSize);
        this.maximumPoolSize = apolloConfig.getProperty("apoolo.dynamicthreadpool.maximumPoolSize", maximumPoolSize);
        this.keepAliveTime = apolloConfig.getProperty("apoolo.dynamicthreadpool.keepAliveTime", keepAliveTime);
        this.workQueueCapacity = apolloConfig.getProperty("apoolo.dynamicthreadpool.workQueueCapacity", workQueueCapacity);

        // 使用最新的配置更新线程池
        if (dynamicThreadPool != null)
        {
            updateThreadPoolConfig();
        }
    }

    // 更新线程池配置
    private void updateThreadPoolConfig()
    {
        // 动态更新线程池的核心线程数、最大线程数和队列容量
        dynamicThreadPool.setCorePoolSize(Integer.parseInt(corePoolSize));
        dynamicThreadPool.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        DynamicLinkedBlockingQueue<?> queue = (DynamicLinkedBlockingQueue<?>)dynamicThreadPool.getQueue();
        queue.setCapacity(Integer.parseInt(workQueueCapacity));
    }

    // 启动时监听 Apollo 配置变更，动态更新线程池配置
    @PostConstruct
    public void init()
    {
        // 初始化线程池
        dynamicThreadPool = createThreadPool();

        // Apollo 配置变更监听
        apolloConfig.addChangeListener(changeEvent ->
        {
            loadThreadPoolConfig(); // 更新配置
            log.info(
                    "ThreadPoolConfig has been updated: " +
                    "corePoolSize = " + corePoolSize + ", " +
                    "maximumPoolSize = " + maximumPoolSize + ", " +
                    "keepAliveTime = " + keepAliveTime + ", " +
                    "workQueueCapacity = " + workQueueCapacity);
        });
    }

    // 创建线程池实例
    private DynamicThreadPool_bak createThreadPool()
    {
        return new DynamicThreadPool_bak
                (
                    Integer.parseInt(corePoolSize),     // 核心线程数
                    Integer.parseInt(maximumPoolSize),  // 最大线程数
                    Long.parseLong(keepAliveTime),      // 空闲线程存活时间
                    TimeUnit.SECONDS,                   // 超时时间单位
                    new DynamicLinkedBlockingQueue<>(Integer.parseInt(workQueueCapacity)) // 工作队列容量
                );
    }

    // 创建并提供线程池 Bean
    @Bean
    public DynamicThreadPool_bak dynamicThreadPool()
    {
        return dynamicThreadPool;
    }




}
