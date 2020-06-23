

package com.twitter.tweet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.twitter.hbc.core.Client;

@Component
public class TwitterProducer {
	Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate ;
	
	@Autowired
	private Client client;
	
	@Autowired
	private BlockingQueue<String> msgQueue ;
//	@Autowired
//	TwitterMetricsConvertor twitterMetricsConvertor;
	
	@PostConstruct
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				logger.info("Setup");
				
				client.connect();
				
				while (!client.isDone()) {
					String msg = null;
					try {
						msg = msgQueue.poll(10, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
						client.stop();
					}
					if (msg != null) {
						logger.info(msg);
//						TwitterMetricsConvertor convertor = new TwitterMetricsConvertor();
						kafkaTemplate.send("Twitter_tweets",msg);
					}
					else {
                        logger.info("NO message");
                    }
			}
		}},"kafka-thread").start();
		}

	
}
