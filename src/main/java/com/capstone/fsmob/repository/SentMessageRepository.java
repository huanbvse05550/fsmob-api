package com.capstone.fsmob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.fsmob.entity.SentMessage;

public interface SentMessageRepository extends JpaRepository<SentMessage, Integer> {

}
