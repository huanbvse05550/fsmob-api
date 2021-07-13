package com.capstone.fsmob.repository;

import org.springframework.stereotype.Repository;

import com.capstone.fsmob.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

	
}
