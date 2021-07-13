package com.capstone.fsmob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.fsmob.dto.UserTokenDto;
import com.capstone.fsmob.service.NotificationService;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/register")
	public ResponseEntity<String> registerToken(@RequestBody UserTokenDto userTokenDto) {
		boolean savedToken = notificationService.registerToken(userTokenDto);
		if (!savedToken) {
			return new ResponseEntity<>("cannot register token for user= " + userTokenDto.userId,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("register success", HttpStatus.OK);
	}
}
