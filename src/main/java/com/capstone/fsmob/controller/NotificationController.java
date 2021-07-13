package com.capstone.fsmob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.fsmob.dto.NotificationRequestDto;
import com.capstone.fsmob.dto.SubscriptionRequestDto;
import com.capstone.fsmob.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/subscribe")
	public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
		notificationService.subscribeToTopic(subscriptionRequestDto);
	}

	@PostMapping("/unsubscribe")
	public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
		notificationService.unsubscribeFromTopic(subscriptionRequestDto);
	}

	@PostMapping("/send")
	public String sendPnsToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
		return notificationService.sendPnsToDevice(notificationRequestDto);
	}

	@PostMapping("/topic")
	public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
		return notificationService.sendPnsToTopic(notificationRequestDto);
	}

	@PostMapping("/re-send")
	public String resendPnsToDevice(@RequestBody String messageId) {
		return notificationService.resendMessage(messageId)+"";
	}

}
