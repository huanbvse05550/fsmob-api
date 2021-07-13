package com.capstone.fsmob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.fsmob.entity.AppUser;
import com.capstone.fsmob.service.NotificationService;

@RestController
@RequestMapping("/users")
public class UserDeviceController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("")
	public ResponseEntity<List<AppUser>> getAllUserRegistered() {
		List<AppUser> users = notificationService.getAllUser();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
