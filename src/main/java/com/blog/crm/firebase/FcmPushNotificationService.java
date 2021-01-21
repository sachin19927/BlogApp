package com.blog.crm.firebase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;

/**
 * Generic push notification class (Generic Messaging Push notification)
 * 
 * @author DEVARAJ ACHARYA
 * @Version %I%, %G%
 * @since 22-09-2020
 */
@Component
public class FcmPushNotificationService {

	@Value("${fcm.pushNotification.url}")
	private String fcmUrl;

	@Value("${fcm.pushNotification.serverKey}")
	private String serverKey;

	/**
	 * Its used to send notification to a particular mobile
	 * 
	 * @param token    - its used for device identification
	 * @param jsonBody - notification content
	 * @return boolean - if success true else false
	 */
	public boolean sendToSingleUser(String content, String token) {

		String jsonBody = "{\r\n" + "    \"notification\": " + content + ",\r\n" + "    \"to\": \"" + token + "\",\r\n"
				+ " }";
		if (sendPushNotification(jsonBody) == 204) {
			return true;
		}
		return false;
	}

	/**
	 * Send notification to multiple tokens in the same time.
	 * 
	 * @param jsonBody - notification content
	 * @return boolean - if success true else false
	 */
	public boolean sendToGroup(String content, ArrayList<String> tokenList) {

		JSONArray jsArray = new JSONArray(tokenList);
		String jsonBody = "{\r\n" + "    \"notification\": " + content + ",\r\n" + "    \"registration_ids\": "
				+ jsArray + "\r\n" + " }";
		if (sendPushNotification(jsonBody) == 204) {
			return true;
		}
		return false;
	}

	/*
	 * This method will send the push notification and return the status code.
	 */
	private int sendPushNotification(String jsonBody) {
		URL taskUrl;
		int status = 0;
		try {
			taskUrl = new URL(fcmUrl);
			HttpURLConnection conn = (HttpURLConnection) taskUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Authorization", "key=" + serverKey);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.write(jsonBody.getBytes("UTF-8"));
			os.close();
			status = conn.getResponseCode();
			conn.disconnect();
		} catch (Exception e) {
			GenericLogger.error(ModuleName.PUSHSMS, this, e);
		}
		return status;
	}
}
