package com.capstone.fsmob.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDto {

	String topicName;
	List<String> tokens;

	public String getTopicName() {
		return this.topicName;
	}

	public List<String> getTokens() {
		return this.tokens;
	}
}
