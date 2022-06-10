package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.helper.MailHelper;

@Service
public class EmailService {

	@Autowired
	private MailHelper mailHelper;

	public void sendEmail() {
		
		String receiver = "ea101.tibame@gmail.com";
		String subject = "純文字測試 email";
		String mailContent = "Hello 大家早安! 我是 Tayli.";
		
		mailHelper.sendEmail(receiver, subject, mailContent);
	}

	public void send_HTML_Email() throws MessagingException {
		
		String receiver = "ea101.tibame@gmail.com";
		String subject = "HTML 網頁格式測試 email";
		String htmlContent = "<b>Hey 各位</b>,<br><i>一起來測試mail吧!</i>";
		
		mailHelper.send_HTML_Email(receiver, subject, htmlContent);
	}

	public void send_PDF_Email() throws MessagingException, FileNotFoundException {
		
		String receiver = "ea101.tibame@gmail.com";
		String subject = "PDF測試 email";
		String content = "<b>Dear friend</b>,<br><i>這是測試的PDF~~~</i>";
		Boolean isHtml = true;
		String filePath = "classpath:static/test.pdf";
		String outFileName = "FreelanceSuccess.pdf";
		
		mailHelper.send_PDF_Email(receiver, subject, content, isHtml, filePath, outFileName);
	}

	public void send_Inline_Images_Email() throws MessagingException, FileNotFoundException {
		
		String receiver = "ea101.tibame@gmail.com";
		String subject = "圖片測試 email";
		Boolean isHtml = true;
		String picPath = "classpath:static/France.jpg";
		String cidName = "France";
		String content = "<b>Dear 各位</b>,<br><i>分享美麗的坎城:</i>" + "<br><img src='cid:" + cidName
				+ "'/><br><b>Best Regards</b><br>Tayli";

		mailHelper.send_Inline_Images_Email(receiver, subject, content, isHtml, picPath, cidName);
	}

	public void send_HTML_Template_Email() throws MessagingException, IOException {
		
		String receiver = "ea101.tibame@gmail.com";
		String subject = "HTML+Template 測試 email";
		String content = "<b>Hey 各位</b>,<br><i>一起來測試mail吧!</i>";
		String templatePath = "classpath:static/mailTemplate/SimpleMessage.html";
		String replaceTemplate = "${message}";
		
		mailHelper.send_HTML_Template_Email(receiver, subject, content, templatePath, replaceTemplate);
	}

	public void sendMultiplePeopleEmail() {
		
		String receivers = "ea101.tibame@gmail.com,kuanrocker@gmail.com";// multiplePeople
		String subject = "純文字測試 email";
		String mailContent = "Hello 大家早安! 我是 Tayli.";
		
		mailHelper.sendMultiplePeopleEmail(receivers, subject, mailContent);
	}
}
