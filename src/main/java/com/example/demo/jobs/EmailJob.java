package com.example.demo.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.EmailService;
import com.example.demo.utils.ExceptionUtility;

@Component
public class EmailJob extends QuartzJob {

	private static EmailService emailService;

	// 注入的時候，給類的 service 注入
	@Autowired
	public void setSampleJobService(EmailService emailService) {
		EmailJob.emailService = emailService;
	}

	protected void handler(JobExecutionContext context) throws JobExecutionException {
		try {
			// 純文字
			emailService.sendEmail();
			// HTML 網頁格式
			emailService.send_HTML_Email();
			// PDF
			emailService.send_PDF_Email();
			// 圖片
			emailService.send_Inline_Images_Email();
			//HTML + Template
			emailService.send_HTML_Template_Email();
			// 多收信人
			emailService.sendMultiplePeopleEmail();

		} catch (Exception e) {
			System.out.println("處理失敗 - {}" + ExceptionUtility.getStackTrace(e));
		}

	}
}
