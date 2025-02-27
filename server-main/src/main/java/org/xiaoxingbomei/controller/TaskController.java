package org.xiaoxingbomei.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "任务controller",description = "全局任务controller")
@RestController
public class TaskController
{

    /**
     * 添加定时任务
     */
//    @PostMapping("add")
//    public boolean add(@RequestBody ScheduleJob scheduleJob) {
//
//        int insert = scheduleJobMapper.insert(scheduleJob);
//        if (insert == 0) {
//            return false;
//        }
//        /**
//         * 添加成功,并且状态是运行，直接放入任务器
//         */
//        if (scheduleJob.getJobStatus().equals(JobStatus.NORMAL.getStatus())) {
//            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getMethodParams());
//            cronTaskRegistrar.addCronTask(task, scheduleJob.getCronExpression());
//        }
//        return true;
//    }
//
//    /**
//     * 修改定时任务
//     */
//    @PostMapping("update")
//    public boolean update(@RequestBody ScheduleJob scheduleJob) {
//        // 查询修改前任务
//        ScheduleJob existedSysJob = scheduleJobMapper.selectOne(new QueryWrapper<ScheduleJob>().eq("job_id", scheduleJob.getJobId()));
//        if (existedSysJob == null) {
//            return false;
//        }
//        // 修改任务
//        int update = scheduleJobMapper.update(scheduleJob, new UpdateWrapper<ScheduleJob>().eq("job_id", scheduleJob.getJobId()));
//        if (update == 0) {
//            return false;
//        }
//        // 修改成功,则先删除任务器中的任务,并重新添加
//        ScheduleRunnable task1 = new ScheduleRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
//        cronTaskRegistrar.removeCronTask(task1);
//        // 如果修改后的任务状态是运行就加入任务器
//        if (scheduleJob.getJobStatus().equals(JobStatus.NORMAL.getStatus())) {
//            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getMethodParams());
//            cronTaskRegistrar.addCronTask(task, scheduleJob.getCronExpression());
//        }
//        return true;
//    }
//
//    /**
//     * 删除任务
//     */
//    @PostMapping("del/{jobId}")
//    public boolean del(@PathVariable("jobId") Integer jobId) {
//        // 先查询要删除的任务信息
//        ScheduleJob existedSysJob = scheduleJobMapper.selectOne(new QueryWrapper<ScheduleJob>().eq("job_id", jobId));
//        // 删除
//        int del = scheduleJobMapper.delete(new QueryWrapper<ScheduleJob>().eq("job_id", jobId));
//        if (del == 0)
//            return false;
//        else {// 删除成功时要清除定时任务器中的对应任务
//            ScheduleRunnable task = new ScheduleRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
//            cronTaskRegistrar.removeCronTask(task);
//            return true;
//        }
//
//    }
//
//    /**
//     * 停止/启动任务
//     */
//    @PostMapping("changesStatus/{jobId}/{stop}")
//    public boolean changesStatus(@PathVariable("jobId") Integer jobId, @PathVariable("stop") Integer stop) {
//
//        // 查询修改后的任务信息
//        ScheduleJob existedSysJob = scheduleJobMapper.selectOne(new QueryWrapper<ScheduleJob>().eq("job_id", jobId));
//        if (existedSysJob == null) {
//            return false;
//        }
//        // 修改任务状态
//        existedSysJob.setJobStatus(stop);
//        existedSysJob.setJobId(jobId);
//        scheduleJobMapper.updateById(existedSysJob);
//
//        // 如果状态是1则添加任务
//        ScheduleRunnable task = new ScheduleRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
//
//        if (existedSysJob.getJobStatus().equals(JobStatus.NORMAL.getStatus())) {
//            cronTaskRegistrar.addCronTask(task, existedSysJob.getCronExpression());
//        } else {
//            // 否则清除任务
//            cronTaskRegistrar.removeCronTask(task);
//        }
//        return true;
//    }
}
