
package com.twitter.tweet.mail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MailConfig implements CommandLineRunner {

	
	public static void main(String[] args) {
		SpringApplication.run(MailConfig.class, args);
	}

	@Override
	public void run(String... args) {

		

		try {

//            sendEmail();
			
			//sendEmailWithAttachment();

		} catch (org.springframework.messaging.MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done");

	}

	/*
	 * void sendEmail() {
	 * 
	 * SimpleMailMessage msg = new SimpleMailMessage();
	 * msg.setTo("shivam.29.rathore@gmail.com");
	 * 
	 * msg.setSubject("Tweezer");
	 * msg.setText("Hi there kindly look at the attached file");
	 * 
	 * javaMailSender.send(msg);
	 */
	
}