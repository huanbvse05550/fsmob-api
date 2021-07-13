package com.capstone.fsmob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.fsmob.entity.FcmRegisterToken;

@Repository
public interface FcmRegisterTokenRepository extends JpaRepository<FcmRegisterToken, Integer> {

	@Query(value = "SELECT fcm.token FROM FcmRegisterToken as fcm INNER JOIN AppUser u ON u.userId = fcm.userId WHERE u.userId =:userId")
	List<String> findTokenByUserId(@Param("userId") String userId);

}
