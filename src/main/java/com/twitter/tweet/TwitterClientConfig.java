package com.twitter.tweet;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Configuration
public class TwitterClientConfig {

	@Bean
	public BlockingQueue<String> blockingQueue() {
		return new LinkedBlockingQueue<String>(10000);
	}

	@Bean
	@Autowired
	public Client createTwitterClient(BlockingQueue<String> msgQueue) {
		String consumerKey = "UX4fqdjn9EYTP18j8GfBzbwE6";
		String consumerSecret = "uQS58DiE8woKxwUOFAKJP709xNM0fZdZjlaxdVBfQV50ZCjJas";
		String token = "1333944564-fFgaUs4zl5pE30WS9Sp6U2e1MOuigoWNe1JFNCY";
		String secret = "sJ76YCSzYmfYortyNWTG5tetyNwLMkXSRSaDl3n5lavZ2";

		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		List<Long> followings = Lists.newArrayList(1234L, 566788L);
		List<String> terms = Lists.newArrayList( "@confluent");
		hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a configuration file
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

		ClientBuilder builder = new ClientBuilder().name("Hosebird-Client-01") // optional: mainly for the logs
				.hosts(hosebirdHosts).authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));
		// optional: use this if you want to process client events

		Client hosebirdClient = builder.build();
		// Attempts to establish a connection.

		return hosebirdClient;

	}
}
