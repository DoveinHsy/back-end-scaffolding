package org.xiaoxingbomei.config.Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public final class BusinessThreadPoolExecutor
{


    private static final int CORE_POOL_SIZE = 5;

    private static final int MAX_POOL_SIZE = 10;

    private static final long KEEP_ALIVE_TIME = 10;

    private static final int MAX_BLOCKING_DE_QUE_SIZE = 1000000;

    private  BusinessThreadPoolExecutor() {}

    /**
     * ThreadPoolExecutorSingleTon
     */
    private enum ThreadPoolExecutorSingleTon
    {
        INSTANCE;
        private final ThreadPoolExecutor instance;

        /**
         * IllegalArgumentException - 如果 corePoolSize 或 keepAliveTime 小于零，或者 maximumPoolSize 小于或等于零，或者 corePoolSize 大于 maximumPoolSize。
         * NullPointerException - 如果 workQueue 为 null
         */
        ThreadPoolExecutorSingleTon()
        {
            instance = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(MAX_BLOCKING_DE_QUE_SIZE), new  BusinessPolicy());
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
class  BusinessPolicy implements RejectedExecutionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BusinessPolicy.class);

    /**
     * Creates a {@code DiscardPolicy}.
     */
    BusinessPolicy() {
    }

    /**
     * 处理拒绝执行
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e)
    {
        try
        {
            e.getQueue().put(r);
        } catch (InterruptedException e1)
        {
            LOGGER.error("处理拒绝执行", e1);
            Thread.currentThread().interrupt();
        }
    }
}