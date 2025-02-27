//package org.xiaoxingbomei.config.Thread;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.ConfigService;
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
//import org.xiaoxingbomei.entity.TaskWithTimestamp;
//import org.xiaoxingbomei.entity.ThreadPoolMonitor;
//
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * 动态线程池实现与配置类
// * 整合配置管理和线程池实现，支持参数动态更新
// */
//@Slf4j
//@Configuration
//public class DynamicThreadPool extends ThreadPoolExecutor {
//
//    @ApolloConfig
//    private Config apolloConfig; // Apollo配置中心注入
//
//    private DynamicLinkedBlockingQueue<Runnable> dynamicQueue;
//
//    /**
//     * 私有化构造函数实现强制通过Spring容器管理
//     */
//    private DynamicThreadPool(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              DynamicLinkedBlockingQueue<Runnable> workQueue) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
//        this.dynamicQueue = workQueue;
//    }
//
//    /**
//     * 线程池实例化方法（静态工厂方法解决循环依赖）
//     */
//    @Bean
//    public static DynamicThreadPool dynamicThreadPool() {
//        // 1. 初始化Apollo配置
//        Config config = ConfigService.getAppConfig();
//
//        // 2. 获取初始配置值（带默认值）
//        int corePoolSize = Integer.parseInt(config.getProperty(
//                "apoolo.dynamicthreadpool.corePoolSize", "5"));
//        int maxPoolSize = Integer.parseInt(config.getProperty(
//                "apoolo.dynamicthreadpool.maximumPoolSize", "10"));
//        long keepAlive = Long.parseLong(config.getProperty(
//                "apoolo.dynamicthreadpool.keepAliveTime", "60"));
//        int queueCapacity = Integer.parseInt(config.getProperty(
//                "apoolo.dynamicthreadpool.workQueueCapacity", "100"));
//
//        // 3. 创建带动态队列的线程池实例
//        DynamicLinkedBlockingQueue<Runnable> queue = new DynamicLinkedBlockingQueue<>(queueCapacity);
//        DynamicThreadPool pool = new DynamicThreadPool(
//                corePoolSize, maxPoolSize, keepAlive, TimeUnit.SECONDS, queue);
//
//        // 4. 注册配置变更监听器
//        config.addChangeListener(changeEvent ->
//                pool.handleConfigChange(changeEvent, config));
//
//        return pool;
//    }
//
//    /**
//     * 处理配置变更事件
//     */
//    private void handleConfigChange(ConfigChangeEvent event, Config config) {
//        // 核心线程数更新
//        if (event.isChanged("apoolo.dynamicthreadpool.corePoolSize")) {
//            int newCore = Integer.parseInt(config.getProperty(
//                    "apoolo.dynamicthreadpool.corePoolSize", "5"));
//            this.updateCorePoolSize(newCore);
//            log.info("Dynamic update corePoolSize to: {}", newCore);
//        }
//
//        // 最大线程数更新
//        if (event.isChanged("apoolo.dynamicthreadpool.maximumPoolSize")) {
//            int newMax = Integer.parseInt(config.getProperty(
//                    "apoolo.dynamicthreadpool.maximumPoolSize", "10"));
//            this.updateMaximumPoolSize(newMax);
//            log.info("Dynamic update maximumPoolSize to: {}", newMax);
//        }
//
//        // 队列容量更新
//        if (event.isChanged("apoolo.dynamicthreadpool.workQueueCapacity")) {
//            int newCapacity = Integer.parseInt(config.getProperty(
//                    "apoolo.dynamicthreadpool.workQueueCapacity", "100"));
//            this.dynamicQueue.setCapacity(newCapacity);
//            log.info("Dynamic update queueCapacity to: {}", newCapacity);
//        }
//    }
//
//    // --------------- 以下为线程池功能增强实现 ---------------
//
//    @Override
//    protected void beforeExecute(Thread t, Runnable r) {
//        if (r instanceof TaskWithTimestamp) {
//            long enqueueTime = ((TaskWithTimestamp) r).getInQueueTime();
//            log.debug("Task waited {}ms in queue", System.currentTimeMillis() - enqueueTime);
//        }
//        super.beforeExecute(t, r);
//    }
//
//    @Override
//    protected void afterExecute(Runnable r, Throwable t) {
//        collectAndLogMetrics();
//        super.afterExecute(r, t);
//    }
//
//    private void collectAndLogMetrics() {
//        ThreadPoolMonitor metrics = new ThreadPoolMonitor(
//                getActiveCount(),
//                getCorePoolSize(),
//                getMaximumPoolSize(),
//                getQueue().size(),
//                dynamicQueue.getCapacity(),
//                getCompletedTaskCount()
//        );
//        log.info("ThreadPool Metrics: {}", metrics);
//    }
//
//    public void updateCorePoolSize(int newSize) {
//        setCorePoolSize(newSize);
//        if (newSize > getMaximumPoolSize()) {
//            setMaximumPoolSize(newSize);
//        }
//    }
//
//    public void updateMaximumPoolSize(int newSize) {
//        setMaximumPoolSize(newSize);
//        if (newSize < getCorePoolSize()) {
//            setCorePoolSize(newSize);
//        }
//    }
//
//    /**
//     * 获取线程池实时状态快照
//     */
//    public String getLiveStatus() {
//        return String.format(
//                "PoolSize: %d (Core: %d, Max: %d), Active: %d, Queue: %d/%d",
//                getPoolSize(),
//                getCorePoolSize(),
//                getMaximumPoolSize(),
//                getActiveCount(),
//                getQueue().size(),
//                dynamicQueue.getCapacity()
//        );
//    }
//}