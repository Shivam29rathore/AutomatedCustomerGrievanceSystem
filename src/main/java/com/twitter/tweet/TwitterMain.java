package com.twitter.tweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterMain{

	@Autowired
	TwitterProducer TwitterProducer;
	public static void main(String[] args) {

		SpringApplication.run(TwitterMain.class, args);
	}
}
