package com.twitter.tweet;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendMailScheduler {
	
	@Autowired
	private JavaMailSender javaMailSender;

	
	@Scheduled(initialDelay = 1000 ,fixedDelay = 1000000)
	void sendEmailWithAttachment() throws MessagingException, IOException {

		System.out.println("Sending Email...");
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("shivam.29.rathore@gmail.com");
		String l1 = "Hi there !  ";
		String l2 = "I am Twizeer ,a TweetBot";
		String l3 = "I am happy to help you !";
		String l4 = "Get this data saved for your reference .";
		String l5 = "This is a system a system generated mail.";

		helper.setSubject("Twitter Data ");
		helper.setText(l1 + "<br>" + l2 + "<br>" + l3 + "<br>" + l4 + "<br>" + l5 + "<br>"

				, true);

		helper.addAttachment("Tweets.xlsx", new File("Tweets.xlsx"));

		javaMailSender.send(msg);
		System.out.println("Send Email...");

	}

}
