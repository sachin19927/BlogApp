package com.blog.crm.firebase.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.firebase.FcmPushNotificationService;
import com.blog.crm.logger.GenericLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author DEVARAJ ACHARYA
 * @Version %I%, %G%
 * @since 22-09-2020
 */

@Controller
public class FcmNotificationController {

	@Autowired
	private FcmPushNotificationService pushNotificationService;

	/**
	 * {@link UserService}
	 */
	/*
	 * @Autowired UserService userService;
	 */

	/**
	 * This is a test method to send a push notification to the single user.
	 * 
	 * @return status message.
	 */
	@GetMapping("/sendToSingleUserTest")
	public @ResponseBody String sendToSingleUserTest() {

		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put("title", "FCM Message");
			jsonBody.put("body", "This is an FCM Message");
			jsonBody.put("click_action", "http://localhost:8080/BsmartFramework/welcome");
			jsonBody.put("icon", "./assets/firebasenotification/icon/productLogo.png");
		} catch (JSONException e) {
			GenericLogger.error(ModuleName.PUSHSMS, this, e);
			return "Failed to sent FCM Notification ";
		}

		String token = "fUxYxuCoXnqOvBc8OLTMKO:APA91bHSeh-JpX67dHecqno9Qw8QvnFd_mY4F1KslHq5r0FDXOnU1UAv6ZN_f-scHMua6i8nJDyTkDJSqbuzM2OHTR6UwSel3VoNb8vDJ2LpFQ1JqJ4QdhVrdZwOgsgGH495M4fQPi8o";
		pushNotificationService.sendToSingleUser(jsonBody.toString(), token);
		return "FCM Notification is sent successfully";
	}

	/**
	 * This is a test method to send a push notification to the group of user.
	 * 
	 * @return status message.
	 */
	@GetMapping("/sendToGroupTest")
	public @ResponseBody String sendToGroupTest() {
		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put("title", "FCM Message");
			jsonBody.put("body", "This is an FCM Message");
			jsonBody.put("icon", "./assets/firebasenotification/icon/productLogo.png");
		} catch (JSONException e) {
			GenericLogger.error(ModuleName.PUSHSMS, this, e);
			return "Failed to sent FCM Notification ";
		}

		// get the list of token.
		ArrayList<String> tokenList = null;// userService.getAllToken();

		pushNotificationService.sendToGroup(jsonBody.toString(), tokenList);

		return "FCM Notification is sent successfully";
	}

	/**
	 * This method will Save the generated fcm token to particular user.
	 * 
	 * @param token FCM Token
	 * @return status message.
	 */
	@PostMapping("/saveFcmToken")
	public @ResponseBody String updateFcmToken(HttpServletRequest request) {
		JsonObject requestObjectJson = new JsonObject();
		GenericLogger.info(ModuleName.PUSHSMS, this, "Calling save Fcm token");
		try {
			requestObjectJson = JsonParser
					.parseString(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
			String token = requestObjectJson.get("token").getAsString();

			return null;// userService.updateFcmToken(token);

		} catch (Exception e) {
			return "failed to add fcm token..!!";

		}

	}

}
