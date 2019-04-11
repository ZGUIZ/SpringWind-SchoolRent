package com.baomidou.springwind.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.SysTask;
import com.baomidou.springwind.service.ISysTaskService;
import com.sun.istack.internal.logging.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JobManager {
    private Logger log = Logger.getLogger(JobManager.class);

    @Autowired
    private ISysTaskService sysTaskService;

    private static Scheduler getScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    public void addJob() throws Exception{
        List<SysTask> sysTaskList = sysTaskService.selectList(new EntityWrapper<>());

        if(sysTaskList == null || sysTaskList.size() <= 0){
            return;
        }

        Scheduler scheduler = getScheduler();

        for(SysTask sysTask : sysTaskList){
            int i = 0;
            Class cls = Class.forName(sysTask.getBeanClass());
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(sysTask.getJobName(),sysTask.getJobGroup()).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+i,"triggerGroup"+i)
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysTask.getCronExpression())).startNow().build();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
            i++;
        }
    }
}
