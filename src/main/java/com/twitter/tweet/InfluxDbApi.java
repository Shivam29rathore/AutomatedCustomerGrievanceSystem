package com.twitter.tweet;

import com.twitter.tweet.model.InfluxModel;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class InfluxDbApi {
	
	public static void  addToInflux(InfluxModel influxModel)
	{
		InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");	
		influxDB.setDatabase("sentiments");

		influxDB.write(Point.measurement("twitter_sentiment")
				.fields(influxModel.getFields())
				.tag(influxModel.getTags())
		    .build());
		influxDB.close();
	}

}
