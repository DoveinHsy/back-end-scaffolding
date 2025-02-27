package org.xiaoxingbomei.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 动态线程池监控
 * 用于存储线程池状态
 */
@Getter
@Setter
@ToString
public class ThreadPoolMonitor
{
    private int activeCount;    // 活跃线程数
    private int maxPoolSize;    // 最大线程数
    private int queueSize;      // 队列中任务数量
    private int queueCapacity;  // 队列容量
    private Long taskWaitTime;  // 任务阻塞时间（入队到执行开始的时间）

}
