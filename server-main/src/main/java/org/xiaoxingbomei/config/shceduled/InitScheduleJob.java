package org.xiaoxingbomei.config.shceduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * 初始化定时任务
 */
//@Service
//@Slf4j
//public class InitScheduleJob implements CommandLineRunner
//{
//
//    final ScheduleJobMapper scheduleJobMapper;
//
//    private final CronTaskRegistrar cronTaskRegistrar;
//
//    public InitScheduleJob(ScheduleJobMapper scheduleJobMapper, CronTaskRegistrar cronTaskRegistrar) {
//        this.scheduleJobMapper = scheduleJobMapper;
//        this.cronTaskRegistrar = cronTaskRegistrar;
//    }
//    @Override
//    public void run(String... args) {
//        // 初始加载数据库里状态为正常的定时任务
//        List<ScheduleJob> jobList = scheduleJobMapper.selectList(new QueryWrapper<ScheduleJob>().eq("job_status", 1));
//        if (CollectionUtils.isNotEmpty(jobList)) {
//            for (ScheduleJob job : jobList) {
//                ScheduleRunnable task = new ScheduleRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
//                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
//            }
//            logger.info("定时任务已加载完毕...");
//        }
//    }
//}