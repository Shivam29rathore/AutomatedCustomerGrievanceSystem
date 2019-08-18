package com.twitter.tweet;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.tweet.model.InfluxModel;
import com.twitter.tweet.model.Tweet;
import com.twitter.tweet.sentiment.SentimentAnalyzer;
import com.twitter.tweet.sentiment.SentimentResult;

@Service
public class TwitterConsumer {

	@KafkaListener(topics = "Twitter_tweets", groupId = "Twitter_tweets")
	public void consume(String message) {

		ObjectMapper ojm = new ObjectMapper();
		try {

			String tweetUrl = "https://twitter.com/statuses/";
			JsonNode node = ojm.readTree(message);
			String id = node.get("id").asText();
			String CreatedAt = node.get("created_at").asText();
			String text = node.get("text").toString();
			Tweet tweet = new Tweet();

			ArrayList<String> list = new ArrayList<String>();
			list.add("recharge");
			list.add("wallet");
			list.add("cashback");
			list.add("dth");
			list.add("mobile");
			list.add("electricity");

			String FinalItem = null;

			for (String item : list) {
				if (text.contains(item)) {
					FinalItem = item;
					break;
				}
			}

			tweetUrl += id;
			JsonNode userNode = node.get("user");

			String followersCount = userNode.get("followers_count").toString();
			String screenName = userNode.get("name").toString();
			userNode.get("screen_name").toString();
			String userDescription = userNode.get("description").toString();
			String userLocation = userNode.get("location").toString();
			String Name = userNode.get("name").toString();
			String verifiedAccount = userNode.get("verified").toString();
			SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
			try {
				sentimentAnalyzer.initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
			SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(text);

			tweet = new Tweet();
			tweet.setTweetText(text);
			tweet.setFollowerCnt(followersCount);
			tweet.setSentimentType(String.valueOf(sentimentResult.getSentimentType()));
			tweet.setSentimentScore(String.valueOf(sentimentResult.getSentimentScore()));
			tweet.setTweetURL(tweetUrl);
			tweet.setVerifiedAccount(verifiedAccount);
			tweet.setUserInfo(userDescription);
			tweet.setUserName(screenName);
			tweet.setCreatedAt(CreatedAt);
			tweet.setName(Name);
			tweet.setLocation(userLocation);
			tweet.setFinalitem(FinalItem);

			InfluxModel influxModel = new InfluxModel("twitter_sentiment");
			influxModel.addTag("userName", screenName);
			influxModel.addField("negitive",
					(double) Math.round(sentimentResult.getSentimentClass().getNegative() * 100d));
			influxModel.addField("positive",
					(double) Math.round(sentimentResult.getSentimentClass().getPositive() * 100d));
			influxModel.addField("Neutral",
					(double) Math.round(sentimentResult.getSentimentClass().getNeutral() * 100d));

			InfluxDbApi.addToInflux(influxModel);

			ExcelUtility.writeToExcelInMultiSheets("Tweets.xlsx", "All Tweets", tweet);

			if (Double.valueOf(tweet.getSentimentScore()) > 2.0) {
				ExcelUtility.writeToExcelInMultiSheets("Tweets.xlsx", "Positive Tweets", tweet);
			}
			if (Double.valueOf(tweet.getSentimentScore()) == 2.0) {
				ExcelUtility.writeToExcelInMultiSheets("Tweets.xlsx", "Neutral Tweets", tweet);
			}
			if (Double.valueOf(tweet.getSentimentScore()) < 2.0) {
				ExcelUtility.writeToExcelInMultiSheets("Tweets.xlsx", "Negative Tweets", tweet);
			}
			if (FinalItem != null) {
				ExcelUtility.writeToExcelInMultiSheets("Tweets.xlsx", FinalItem, tweet);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}