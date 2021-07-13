package com.capstone.fsmob.entity;


import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {

	@Id
	@Column(name = "user_id")
	private String userId;
	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_num")
	private String phoneNumber;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

}
