package com.capstone.fsmob.service;

import com.capstone.fsmob.dto.NotificationRequestDto;
import com.capstone.fsmob.dto.SubscriptionRequestDto;
import com.capstone.fsmob.dto.UserTokenDto;
import com.capstone.fsmob.entity.AppUser;
import com.capstone.fsmob.entity.FcmRegisterToken;
import com.capstone.fsmob.entity.SentMessage;
import com.capstone.fsmob.entity.SentTarget;
import com.capstone.fsmob.repository.AppUserRepository;
import com.capstone.fsmob.repository.FcmRegisterTokenRepository;
import com.capstone.fsmob.repository.SentMessageRepository;
import com.capstone.fsmob.repository.SentTargetRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NotificationService {

	@Value("${app.firebase-config}")
	private String firebaseConfig;

	private FirebaseApp firebaseApp;

	@Autowired
	private AppUserRepository appUserRepos;

	@Autowired
	private FcmRegisterTokenRepository tokenRepos;

	@Autowired
	private SentMessageRepository sentMessageRepository;

	@Autowired
	private SentTargetRepository sentTargetRepository;

	@PostConstruct
	private void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream()))
					.build();

			if (FirebaseApp.getApps().isEmpty()) {
				this.firebaseApp = FirebaseApp.initializeApp(options);
			} else {
				this.firebaseApp = FirebaseApp.getInstance();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
		try {
			FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
					subscriptionRequestDto.getTopicName());
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}

	public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
		try {
			FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
					subscriptionRequestDto.getTopicName());
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}

	public String sendPnsToDevice(NotificationRequestDto notificationRequestDto) {
		// [START send_multicast]
		// Create a list containing up to 500 registration tokens.
		// These registration tokens come from the client FCM SDKs.
		int count = 0;
		List<String> registrationTokens = new ArrayList<>();
		for (String userId : notificationRequestDto.getTarget()) {
			List<String> targetApp = tokenRepos.findTokenByUserId(userId);
			if (targetApp != null && !targetApp.isEmpty()) {
				registrationTokens.addAll(targetApp);
			}
		}

		MulticastMessage message = MulticastMessage.builder().putData("score", "850").putData("time", "2:45")
				.addAllTokens(registrationTokens).build();
		BatchResponse response = null;
		try {
			response = FirebaseMessaging.getInstance().sendMulticast(message);
			count = response.getSuccessCount();
			if (count > 0) {
				SentMessage mess = new SentMessage();
				mess.setTitle(notificationRequestDto.getTitle());
				mess.setBody(notificationRequestDto.getBody());
				mess.setDate(System.currentTimeMillis() + "");
				SentMessage sentMessage = sentMessageRepository.save(mess);
				for (String userId : notificationRequestDto.getTarget()) {
					SentTarget targeted = new SentTarget();
					targeted.setUserId(userId);
					targeted.setMessageId(sentMessage.getId());
					sentTargetRepository.save(targeted);
				}
			}
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// See the BatchResponse reference documentation
		// for the contents of response.
		return count + "";
		// [END send_multicast]
	}

	public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
		Message message = Message.builder().setTopic(notificationRequestDto.getTarget()[0])
				.setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
				.putData("content", notificationRequestDto.getTitle()).putData("body", notificationRequestDto.getBody())
				.build();

		String response = null;
		try {
			FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}

		return response;
	}

	public int resendMessage(String messageId) {
		JSONObject obj = new JSONObject(messageId);

		int id = Integer.parseInt(obj.getString("messageId"));
		Optional<SentMessage> message = sentMessageRepository.findById(id);
		if (message.isPresent()) {
			NotificationRequestDto ntDto = new NotificationRequestDto();
			List<String> userIdList = sentTargetRepository.findUserIdByMessageId(id);
			ntDto.setBody(message.get().getBody());
			ntDto.setTitle(message.get().getTitle());
			ntDto.setTarget(userIdList.toArray(new String[userIdList.size()]));
			sendPnsToDevice(ntDto);
		}
		return 0;
	}

	public boolean registerToken(UserTokenDto userTokenDto) {
		Optional<AppUser> appUser = appUserRepos.findById(userTokenDto.userId);
		if (!appUser.isPresent()) {
			AppUser user = new AppUser();
			user.setUserId(userTokenDto.userId);
			user.setFullName(userTokenDto.fullName);
			AppUser savedUser = appUserRepos.save(user);
			if (savedUser == null) {
				return false;
			}
		}
		FcmRegisterToken token = new FcmRegisterToken();
		token.setToken(userTokenDto.key);
		token.setUserId(userTokenDto.userId);
		tokenRepos.save(token);
		return true;
	}

	public List<AppUser> getAllUser() {
		return appUserRepos.findAll();
	}
}
