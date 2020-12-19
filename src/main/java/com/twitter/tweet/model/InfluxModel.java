package com.twitter.tweet.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class InfluxModel {
	String measurementName;
	Map<String,String> tags;
	Map<String,Object> fields;

	public String getMeasurementName() {
		return measurementName;
	}

	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public InfluxModel(String measurement) {
		tags = new HashMap();
		fields = new HashMap();
		this.measurementName = measurement;
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
