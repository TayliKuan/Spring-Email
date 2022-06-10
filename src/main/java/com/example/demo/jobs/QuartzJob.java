package com.example.demo.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Quartz job 一定要繼承這個才會自動注入
 */
public abstract class QuartzJob extends QuartzJobBean {
	
	//主要用來對Spring Web Application context 的class 提供@Autowired 注入功能。
	//理由 https://www.jianshu.com/p/125258ada53b
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        handler(context);
    }
    
    protected abstract void handler(JobExecutionContext context) throws JobExecutionException;

}
