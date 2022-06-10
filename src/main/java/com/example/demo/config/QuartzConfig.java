package com.example.demo.config;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.example.demo.jobs.EmailJob;
import com.example.demo.listener.EmailListener;

/**
 * QUARTZ 排程設定
 *
 */
@Configuration
public class QuartzConfig {
    public static final String TEST_GROUP = "testGroup";
    @Autowired
    private EmailListener emailListener;
    

    /**
     * Test 排程(要執行的job任務) step 3
     *
     */
    @Bean
    public JobDetailFactoryBean testJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(EmailJob.class);
        jobDetailFactoryBean.setDescription("Email 排程");
        jobDetailFactoryBean.setName("Tayli");
        jobDetailFactoryBean.setGroup(TEST_GROUP);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }
    
    /**
     * Test 排程 TRIGGER(觸發)   step 4
     *
     * @param testJobDetail
     * @return TestTrigger
     */
    @Bean
    public CronTriggerFactoryBean testTrigger(JobDetail testJobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(testJobDetail);
//        cronTriggerFactoryBean.setCronExpression("0 0/5 * * * ?"); //每5分鐘執行一次任務
        cronTriggerFactoryBean.setCronExpression("0/20 * * * * ?"); //20秒執行一次
        return cronTriggerFactoryBean;
    }
    
    /**
     * SchedulerFactoryBean (把你定製的FactoryBean插入容器中)   
     *
     * @param testTrigger
     * @return scheduler
     */
    @Bean
    public SchedulerFactoryBean scheduler(CronTrigger testTrigger) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTriggers(testTrigger);
        // 每次更新排程時間
        schedulerFactory.setOverwriteExistingJobs(true);
        
        schedulerFactory.setGlobalJobListeners(emailListener);
        return schedulerFactory;
    }
    
}
