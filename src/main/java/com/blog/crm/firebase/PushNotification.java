package com.blog.crm.firebase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Generic push notification class (Generic Messaging Push notification)
 * 
 * @author REMITH(BCITS)
 * @version 1.0(23-09-19)
 */
public class PushNotification implements PushNotificationService {

	private final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	private String SERVER_KEY;
	private JSONObject root;
	private static final Logger logger = LogManager.getLogger(PushNotification.class);

	/**
	 * @param serverToken - token from server
	 * @param isHybrid    - Pushing to Cordova kind of Hybrid application
	 * @param title       - Title/Subject for the notification
	 * @param message     - Message body
	 * @param senderName  - Sender Name- It can be any application user or
	 *                    administrator
	 * @param senderId    - Sender Id- It is the user name of the sender. (Unique
	 *                    identifier)
	 * @throws Exception in case of generic Exception
	 */
	public PushNotification(String serverToken, boolean isHybrid, String title, String message, String senderName,
			String senderId) throws Exception {
		this.SERVER_KEY = serverToken;

		if (serverToken == null || serverToken.trim().length() < 20) {
			throw new Exception("Invlaid Server Token");
		}

		if (title == null || title.trim().length() < 3) {
			throw new Exception("Invlaid Title. Length must be minimum 3");
		}

		if (message == null || message.trim().length() < 10) {
			throw new Exception("Invlaid Message. Length must be 10");
		}

		if (senderName == null || senderName.trim().length() < 3) {
			throw new Exception("Invlaid Sender Name");
		}

		if (senderId == null || senderId.trim().isEmpty()) {
			throw new Exception("Invalid Sender ID");
		}

		root = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("title", title);
		data.put("message", message);
		data.put("sender", senderName);
		data.put("senderId", senderId);
		root.put("data", data);

		if (isHybrid) {
			JSONObject noty = new JSONObject();// NOTIFICATION
			noty.put("title", title);
			noty.put("body", message);
			noty.put("sound", "default");
			noty.put("sender", senderName);
			noty.put("senderId", senderId);
			noty.put("click_action", "FCM_PLUGIN_ACTIVITY");
			noty.put("icon", "fcm_push_icon");
			root.put("notification", noty);
		}
	}

	@Override
	public boolean sendToSingleMobile(String token) throws Exception {
		if (token == null || token.trim().isEmpty()) {
			throw new Exception("Invalid Token");
		}
		root.put("to", token);
		return sendPushNotification(false);
	}

	@Override
	public boolean sendToTopic(String topic) throws Exception {
		if (topic == null || topic.trim().isEmpty()) {
			throw new Exception("Invalid Topic");
		}
		root.put("condition", "'" + topic + "' in topics");
		return sendPushNotification(true);
	}

	@Override
	public boolean sendToGroup(JSONArray mobileTokens) throws Exception {
		if (mobileTokens == null) {
			throw new Exception("Null array found.");
		}
		if (mobileTokens.length() > 1000) {
			throw new Exception("Limit exceeds more than 1000");
		}
		if (mobileTokens.length() == 0) {
			throw new Exception("No tokens found");
		}
		root.put("registration_ids", mobileTokens);
		return sendPushNotification(false);
	}

	private boolean sendPushNotification(boolean toTopic) throws IOException, JSONException {
		URL url = new URL(API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);

		logger.info(root.toString());

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(root.toString());
		wr.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		StringBuilder builder = new StringBuilder();
		while ((output = br.readLine()) != null) {
			builder.append(output);
		}
		logger.info(builder);
		String result = builder.toString();

		JSONObject obj = new JSONObject(result);

		if (toTopic) {
			if (obj.has("message_id")) {
				return true;
			}
		} else {
			int success = Integer.parseInt(obj.getString("success"));
			if (success > 0) {
				return true;
			}
		}

		logger.info(builder.toString());

		return false;
	}

}
