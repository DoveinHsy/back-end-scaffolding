package org.xiaoxingbomei.entity;


/**
 * 任务封装类
 * 由于我们需要记录任务的入队时间，通常情况下我们需要为每个任务添加一个时间戳。我们可以通过封装任务对象来实现。
 * 在创建任务时，你可以将其包装成 TaskWithTimestamp：
 */
public class TaskWithTimestamp implements Runnable
{

    private long inQueueTime; // 任务入队时间

    public TaskWithTimestamp(Runnable task) {
        this.inQueueTime = System.currentTimeMillis();  // 记录入队时间
        this.task = task;
    }

    private Runnable task;

    public long getInQueueTime() {
        return inQueueTime;
    }

    @Override
    public void run() {
        task.run();  // 执行实际的任务
    }
}