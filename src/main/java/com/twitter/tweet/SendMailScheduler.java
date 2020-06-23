package com.twitter.tweet;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendMailScheduler {
	
	@Autowired
	private JavaMailSender javaMailSender;



	void sendEmailWithAttachment() throws MessagingException, IOException {

		System.out.println("Sending Email...");
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("shivam.29.rathore@gmail.com");
		String l1 = "Hi there !  ";
		String l2 = "I am here to assist in you in your attendance !";
		String l3 = "Your attendance is 70% today .";
		String l4 = "You have attended 7 classes out of 10 .";
		String l5 = "This is a system generated mail.";

		helper.setSubject("Attendance  Data ");
		helper.setText(l1 + "<br>" + l2 + "<br>" + l3 + "<br>" + l4 + "<br>" + l5 + "<br>"

				, true);

		helper.addAttachment("Tweets.xlsx", new File("Tweets.xlsx"));

		javaMailSender.send(msg);
		System.out.println("Send Email...");

	}


}
