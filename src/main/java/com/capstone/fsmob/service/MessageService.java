package com.capstone.fsmob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.fsmob.entity.SentMessage;
import com.capstone.fsmob.repository.SentMessageRepository;

@Service
public class MessageService {

	@Autowired
	private SentMessageRepository sentMessageRepository;

	public List<SentMessage> getAllMessage() {
		return sentMessageRepository.findAll();
	}
}
