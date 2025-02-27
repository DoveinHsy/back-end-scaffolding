package org.xiaoxingbomei.config.shceduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务 配置
 * 1、线程池配置
 */
@Configuration
public class ScheduleConfig
{

    /**
     * 线程池配置
     * ThreadPoolTaskScheduler初始化的线程池的大小为1，多个任务触发时会等待一个执行完毕才执行下一个，所以需要自定义线程池大小
     */
    @Bean
    public TaskScheduler taskScheduler()
    {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(6);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("taskExecutor-");
        return taskScheduler;
    }

    /**
     * 其他配置 待添加
     */
}
