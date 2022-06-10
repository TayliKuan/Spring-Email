package com.example.demo.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener implements JobListener {

	private static final String SYSTEM_DATETIME_PATTERN = "yyyyMMddHHmmss";


	@Override
	public String getName() {
		return "Email_Listener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String jobStartTime = new SimpleDateFormat(SYSTEM_DATETIME_PATTERN).format(context.getFireTime());
		System.out.println("jobName= " + getName());
		System.out.println("jobStartTime= " + jobStartTime);
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobEndTime = new SimpleDateFormat(SYSTEM_DATETIME_PATTERN)
				.format((new Date(context.getFireTime().getTime() + context.getJobRunTime())));
		System.out.println("Email 排程完成時間= " + jobEndTime);
	}
}
