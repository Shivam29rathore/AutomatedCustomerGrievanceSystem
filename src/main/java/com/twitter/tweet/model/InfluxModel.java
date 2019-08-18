package com.twitter.tweet.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class InfluxModel {
	String measurementName;
	Map<String,String> tags;
	Map<String,Object> fields;
	
	public InfluxModel(String mesurement) {
		tags = new HashMap();
		fields = new HashMap();
		this.measurementName = mesurement;
	}
	
	public void addTag(String tagName,String tagVal) {
		tags.put(tagName, tagVal);
	}
	
	
	public void addField(String fieldName,Double fieldVal) {
		fields.put(fieldName, fieldVal);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("'").append(measurementName).append(",");
		for(Map.Entry entry: tags.entrySet())
		{
			buffer.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(",")).append(" ");
		for(Map.Entry entry: fields.entrySet())
		{
			buffer.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(",")).append("'");
		return buffer.toString();
	}
}
