package com.capstone.fsmob.dto;

import lombok.Data;

@Data
public class NotificationRequestDto {

	private String[] target;
	private String title;
	private String body;

	public String[] getTarget() {
		return this.target;
	}

	public String getBody() {
		return this.body;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTarget(String[] target) {
		this.target = target;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
