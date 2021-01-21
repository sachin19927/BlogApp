package com.blog.crm.firebase;

import org.codehaus.jettison.json.JSONArray;

public interface PushNotificationService {


	/**
	 * Its used to send notification to a particular mobile
	 * 
	 * @param token - its used for device identification
	 * @return boolean - if success true else false
	 * @throws Exception in case of any generic Exception
	 */
	boolean sendToSingleMobile(String token) throws Exception;


	/**
	 * Bulk notification based on subscribed topics
	 * 
	 * @param topic - identification topic. Standard application package(for whole project)
	 * @return boolean - if success true else false
	 * @throws Exception in case of any generic exception
	 */
	boolean sendToTopic(String topic) throws Exception;


	/**
	 * Send notification to multiple tokens in the same time.
	 * 
	 * @param mobileTokens - JSON array of tokens. Max allowed tokens 1000 same time.
	 * @return boolean - if success true else false
	 * @throws Exception in case of generic exception
	 */
	boolean sendToGroup(JSONArray mobileTokens) throws Exception;
}
