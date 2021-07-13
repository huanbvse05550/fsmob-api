package com.capstone.fsmob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.fsmob.entity.SentMessage;
import com.capstone.fsmob.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GetMapping("")
	public ResponseEntity<List<SentMessage>> getMessgae() {
		List<SentMessage> sentMessage = messageService.getAllMessage();
		return new ResponseEntity<List<SentMessage>>(sentMessage, HttpStatus.OK);
	}
}
