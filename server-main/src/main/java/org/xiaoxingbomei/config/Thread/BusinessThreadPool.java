package org.xiaoxingbomei.config.Thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 *
 * <pre>
 *     1：当MAX_BLOCKING_DE_QUE_SIZE队列数：满了 -> 才会让最大运行线程是：MAX_POOL_SIZE
 *     2：当MAX_BLOCKING_DE_QUE_SIZE队列数：未满 -> 最大运行线程是：CORE_POOL_SIZE
 * </pre>
 */
@Component
public final class BusinessThreadPool {


    /**
     * 配置核心线程数 - 池中所保存的线程数，包括空闲线程
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 配置最大线程数 - 池中允许的最大线程数
     */
    private static final int MAX_POOL_SIZE = 10;
    /**
     * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     * 参数的时间单位
     */
    private static final long KEEP_ALIVE_TIME = 10;
    /**
     * 最大阻塞队列数
     * 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
     */
    private static final int MAX_BLOCKING_DE_QUE_SIZE = 1000000;


    private BusinessThreadPool() {
    }
    /**
     * ThreadPoolExecutorSingleTon
     */
    private enum ThreadPoolExecutorSingleTon {
        INSTANCE;
        private final ThreadPoolExecutor instance;

        /**
         * IllegalArgumentException - 如果 corePoolSize 或 keepAliveTime 小于零，或者 maximumPoolSize 小于或等于零，或者 corePoolSize 大于 maximumPoolSize。
         * NullPointerException - 如果 workQueue 为 null
         */
        ThreadPoolExecutorSingleTon() {
            instance = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(MAX_BLOCKING_DE_QUE_SIZE), new MyPolicy());
        }

        public ThreadPoolExecutor getInstance() {
            return instance;
        }
    }

    /**
     * getInstance
     */
    public static ThreadPoolExecutor getInstance() {
        return ThreadPoolExecutorSingleTon.INSTANCE.getInstance();
    }
}

/**
 * 拒绝策略
 */
class MyPolicy implements RejectedExecutionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyPolicy.class);

    /**
     * Creates a {@code DiscardPolicy}.
     */
    MyPolicy() {
    }

    /**
     * 处理拒绝执行
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        try {
            e.getQueue().put(r);
        } catch (InterruptedException e1) {
            LOGGER.error("处理拒绝执行", e1);
            Thread.currentThread().interrupt();
        }
    }
}