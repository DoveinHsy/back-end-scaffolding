package org.xiaoxingbomei.config.shceduled;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类
 */
@Component
public class CronTaskRegistrar implements DisposableBean
{

    private final Map<Runnable, ScheduledTaskConfig> scheduledTasks = new ConcurrentHashMap<>(16);

    private final TaskScheduler taskScheduler;

    public CronTaskRegistrar(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    /**
     * 添加任务
     */
    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }

    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }
            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }

    /**
     * 移除任务
     */
    public void removeCronTask(Runnable task) {
        ScheduledTaskConfig scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
            scheduledTask.cancel();
    }

    public ScheduledTaskConfig scheduleCronTask(CronTask cronTask) {
        ScheduledTaskConfig scheduledTask = new ScheduledTaskConfig();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }


    /**
     * 销毁任务
     */
    @Override
    public void destroy() {
        for (ScheduledTaskConfig task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}