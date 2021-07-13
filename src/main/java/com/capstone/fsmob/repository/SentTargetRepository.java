package com.capstone.fsmob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.fsmob.entity.SentTarget;

public interface SentTargetRepository extends JpaRepository<SentTarget, Integer> {

	@Query("SELECT s.userId FROM SentTarget s WHERE s.messageId =:messageId")
	List<String> findUserIdByMessageId(@Param("messageId") int messageId);
}
