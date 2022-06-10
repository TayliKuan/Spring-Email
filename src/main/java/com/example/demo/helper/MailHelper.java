package com.example.demo.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class MailHelper {

	private static final String DEFAULT_SENDER = "ea101.tibame@gmail.com";

	// 參考 https://www.codejava.net/frameworks/spring-boot/email-sending-tutorial
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 寄發 Email (簡單文字內容)
	 * 
	 * @param receiver    收件人
	 * @param subject     主旨
	 * @param mailContent 訊息內容
	 */
	public void sendEmail(String receiver, String Subject, String mailContent) {
		String from = DEFAULT_SENDER;// sender
		String to = receiver; // recipient

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(Subject);
		message.setText(mailContent);

		mailSender.send(message);
	}

	/**
	 * 寄發 Email (HTML 文字內容)
	 * 
	 * @param receiver    收件人
	 * @param subject     主旨
	 * @param htmlContent HTML內容
	 */
	public void send_HTML_Email(String receiver, String Subject, String htmlContent) throws MessagingException {
		String from = DEFAULT_SENDER;// sender
		String to = receiver;// recipient

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(Subject);

		boolean isHtml = true;
		helper.setText(htmlContent, isHtml);

		mailSender.send(message);
	}

	/**
	 * 寄發 Email (PDF)
	 * 
	 * @param receiver    收件人
	 * @param subject     主旨
	 * @param content     訊息內容
	 * @param isHtml      是否為HTML內容
	 * @param filePath    讀取內部PDF路徑
	 * @param outFileName 信中的PDF檔名
	 */
	public void send_PDF_Email(String receiver, String subject, String content, Boolean isHtml, String filePath,
			String outFileName) throws MessagingException, FileNotFoundException {
		String from = DEFAULT_SENDER;// sender
		String to = receiver;// recipient

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, isHtml);

		FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(filePath));
		helper.addAttachment(outFileName, file);

		mailSender.send(message);

	}

	/**
	 * 寄發 Email (圖文內容)
	 * 
	 * @param receiver 收件人
	 * @param subject  主旨
	 * @param content  訊息內容 (需包含內籤cid)
	 * @param isHtml   是否為HTML內容
	 * @param picPath  內部圖片路徑
	 * @param cidName  內籤cid檔名
	 */
	public void send_Inline_Images_Email(String receiver, String subject, String content, Boolean isHtml,
			String picPath, String cid) throws MessagingException, FileNotFoundException {
		String from = DEFAULT_SENDER;// sender
		String to = receiver;// recipient

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, isHtml);

		FileSystemResource resource = new FileSystemResource(ResourceUtils.getFile(picPath));
		helper.addInline(cid, resource);

		mailSender.send(message);
	}

	/**
	 * 寄發 Email (讀取HTML_Template 文字內容)
	 * 
	 * @param receiver        收件人
	 * @param subject         主旨
	 * @param content         訊息內容 (需包含內籤cid)
	 * @param templatePath    HTML_Template路徑
	 * @param replaceTemplate HTML_Template置換content文字
	 */
	public void send_HTML_Template_Email(String receiver, String subject, String content, String templatePath,
			String replaceName) throws MessagingException, IOException {
		String from = DEFAULT_SENDER;// sender
		String to = receiver;// recipient

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);

		String htmlContent = StringUtils.EMPTY;
		File file = ResourceUtils.getFile(templatePath);
		try (InputStream is = new FileInputStream(file)) {

			int i = -1;
			StringBuilder sb = new StringBuilder();
			while ((i = is.read()) != -1) {
				sb.append((char) i);
			}
			String mailContent = content;
			htmlContent = StringUtils.replace(sb.toString(), replaceName, mailContent);
		}
		boolean ishtml = true;
		helper.setText(htmlContent, ishtml);

		mailSender.send(message);
	}

	/**
	 * 寄發 Email (簡單文字內容)
	 * 
	 * @param receivers   收件人，多個收件人以「,」隔開
	 * @param subject     主旨
	 * @param mailContent 訊息內容
	 */
	public void sendMultiplePeopleEmail(String receivers, String subject, String mailContent) {
		String from = DEFAULT_SENDER;// sender
		// 以 , 隔開
		String to = receivers;// multiplePeople

		MimeMessagePreparator preparator = mimeMessage -> {
			String[] sendTo = StringUtils.split(to, ",");
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

			message.setFrom(from);
			message.setTo(sendTo);
			message.setSubject(subject);
			message.setText(mailContent);
		};
		mailSender.send(preparator);
	}
}
