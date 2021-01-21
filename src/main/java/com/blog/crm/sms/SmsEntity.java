package com.blog.crm.sms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.util.ResourceUtils;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;

/**
 * SmsEntity contains SMS properties and SMS sending methods
 * 
 * @author Vema Reddy
 * @version %I%, %G%
 * @since 23-10-19
 */
public class SmsEntity implements Serializable, Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constants
	 */
	private static final String ESANCHAR_HTTP = "ESANCHAR_HTTP";
	private static final String ESANCHAR_OTP = "ESANCHAR_OTP";

	/**
	 * Constants
	 */
	private static String password = "PASSWORD";// SchemaUtils.PASSWORD;

	private String number;

	private List<String> bulknumber;

	private String language;

	private String message;

	private String smsGatewayURL;

	private String smsGatewaySid;

	private String smsGatewayMsgType;

	private String smsGatewayGwid;

	private String smsGatewayUsername;

	private String smsGatewayPwd;

	private String smsUniqueid;

	private String serviceName;

	private String gateWayName;

	private String maskName;

	private String smsConfigPath;

	/**
	 * This Constructor is used to send SMS to single person
	 * 
	 * @param number        - 10digit mobile number
	 * @param message       - Content which need to be sent to consumers/user/person
	 * @param gateWayName   - Name of the gateway
	 * @param smsConfigPath - The sms configuration details
	 */
	public SmsEntity(String number, String message, String gateWayName, String smsConfigPath) {
		super();
		if (ESANCHAR_OTP.equals(gateWayName) || ESANCHAR_HTTP.equals(gateWayName)) {
			this.bulknumber = new ArrayList<>();
			this.bulknumber.add(number);
		}
		this.number = number;
		this.message = message;
		this.gateWayName = gateWayName;
		this.smsConfigPath = smsConfigPath;
		this.getGatewayProperties(gateWayName, smsConfigPath);

	}

	/**
	 * This Constructor is used to send SMS to one or more persons
	 * 
	 * @param bulknumber    - 10digit mobile number
	 * @param message       - Content which need to be sent to consumers/user/person
	 * @param gateWayName   - Name of the gateway
	 * @param smsConfigPath - The sms configuration details
	 */
	public SmsEntity(List<String> bulknumber, String message, String gateWayName, String smsConfigPath) {
		super();
		if (!(ESANCHAR_OTP.equals(gateWayName) || ESANCHAR_HTTP.equals(gateWayName))) {
			StringBuilder mobnumber = new StringBuilder();
			for (int i = 0; i < bulknumber.size(); i++) {
				mobnumber.append(bulknumber.get(i) + ",");
			}
			this.number = mobnumber.substring(0, mobnumber.length() - 1);
		}
		this.bulknumber = bulknumber;
		this.message = message;
		this.gateWayName = gateWayName;
		this.smsConfigPath = smsConfigPath;
		this.getGatewayProperties(gateWayName, smsConfigPath);
	}

	/**
	 * Returns mobile number
	 * 
	 * @return String - mobile number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 
	 * @param number the mobile number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns one or more mobile numbers
	 * 
	 * @return The list of mobile numbers
	 */
	public List<String> getBulknumber() {
		return bulknumber;
	}

	/**
	 * 
	 * @param bulknumber-set the one or more mobile numbers
	 */
	public void setBulknumber(List<String> bulknumber) {
		this.bulknumber = bulknumber;
	}

	/**
	 * Returns language ,this is depending on gateway
	 * 
	 * @return String - by default language is ENG
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * 
	 * @param language - the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Returns Message Content,which need to be send to consumers/users
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message -the message content to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns SMS gateway URL based on the gateway name
	 * 
	 * @return String
	 */
	public String getSmsGatewayURL() {
		return smsGatewayURL;
	}

	/**
	 * 
	 * @param smsGatewayURL The sms gateway name
	 */
	public void setSmsGatewayURL(String smsGatewayURL) {
		this.smsGatewayURL = smsGatewayURL;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSmsGatewaySid() {
		return smsGatewaySid;
	}

	/**
	 * @param smsGatewaySid The gateway service id
	 */
	public void setSmsGatewaySid(String smsGatewaySid) {
		this.smsGatewaySid = smsGatewaySid;
	}

	/**
	 * @return String
	 */
	public String getSmsGatewayMsgType() {
		return smsGatewayMsgType;
	}

	/**
	 * @param smsGatewayMsgType the message type
	 */
	public void setSmsGatewayMsgType(String smsGatewayMsgType) {
		this.smsGatewayMsgType = smsGatewayMsgType;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSmsGatewayGwid() {
		return smsGatewayGwid;
	}

	/**
	 * 
	 * @param smsGatewayGwid The gateway id
	 */
	public void setSmsGatewayGwid(String smsGatewayGwid) {
		this.smsGatewayGwid = smsGatewayGwid;
	}

	/**
	 * Returns SMS gateway username
	 * 
	 * @return String
	 */
	public String getSmsGatewayUsername() {
		return smsGatewayUsername;
	}

	/**
	 * 
	 * @param smsGatewayUsername The gateway user name
	 */
	public void setSmsGatewayUsername(String smsGatewayUsername) {
		this.smsGatewayUsername = smsGatewayUsername;
	}

	/**
	 * Returns SMS gateway passowrd
	 * 
	 * @return String
	 */
	public String getSmsGatewayPwd() {
		return smsGatewayPwd;
	}

	/**
	 * 
	 * @param smsGatewayPwd The gateway password
	 */
	public void setSmsGatewayPwd(String smsGatewayPwd) {
		this.smsGatewayPwd = smsGatewayPwd;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSmsUniqueid() {
		return smsUniqueid;
	}

	/**
	 * 
	 * @param smsUniqueid The sms unique id
	 */
	public void setSmsUniqueid(String smsUniqueid) {
		this.smsUniqueid = smsUniqueid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 * @return String
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * 
	 * @param serviceName The service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Returns gateway name
	 * 
	 * @return String
	 */
	public String getGateWayName() {
		return gateWayName;
	}

	/**
	 * 
	 * @param gateWayName The gateway name
	 */
	public void setGateWayName(String gateWayName) {
		this.gateWayName = gateWayName;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMaskName() {
		return maskName;
	}

	/**
	 * 
	 * @param maskName The mask name
	 */
	public void setMaskName(String maskName) {
		this.maskName = maskName;
	}

	public String getSmsConfigPath() {
		return smsConfigPath;
	}

	public void setSmsConfigPath(String smsConfigPath) {
		this.smsConfigPath = smsConfigPath;
	}

	/**
	 * This method provide the SMS gateway properties
	 * 
	 * @param gatewayName   - it is used to identify the SMS gateway
	 * @param smsConfigPath The sms configuration details
	 */
	public final void getGatewayProperties(String gatewayName, String smsConfigPath) {
		File file = null;
		try {
			file = ResourceUtils.getFile(smsConfigPath);

			// Read File Content
			String content = new String(Files.readAllBytes(file.toPath()));

			JSONObject obj = new JSONObject(content);
			if (obj.length() > 0) {
				JSONObject json = obj.getJSONObject(gatewayName);
				this.smsGatewayURL = json.getString("SMS.GATEWAY.URL");
				this.smsGatewayUsername = json.getString("SMS.GATEWAY.USERNAME");
				this.smsGatewayPwd = json.getString("SMS.GATEWAY.PASSWORD");
				this.smsGatewaySid = json.getString("SMS.GATEWAY.SID");
				this.smsUniqueid = json.getString("SMS.GATEWAY.UNIQUEID");
				this.smsGatewayGwid = json.getString("SMS.GATEWAY.GWID");
				this.maskName = json.getString("SMS.GATEWAY.MASKNAME");
				this.serviceName = json.getString("SMS.GATEWAY.SERVICENAME");
				this.language = json.getString("SMS.GATEWAY.LANGUAGE");
			}

		} catch (IOException | JSONException e) {
			GenericLogger.error(ModuleName.EMAIL, this, e);

		}
	}

	/**
	 * This method is used to send SMS
	 */
	@Override
	public void run() {
		GenericLogger.info(ModuleName.SMS, this, "run method getting called for " + gateWayName);
		switch (gateWayName) {
		case ESANCHAR_OTP:
			try {
				esancharSMSService();
			} catch (IOException e) {
				GenericLogger.error(ModuleName.SMS, this, e);
			}
			break;
		case ESANCHAR_HTTP:
			try {
				esancharSMSService();
			} catch (IOException e) {
				GenericLogger.error(ModuleName.SMS, this, e);
			}
			break;
		case "GUPSHUP":
			gupshupSMSService();
			break;
		case "SMSLANE":
			smslaneService();
			break;
		case "GODADDY":
			godaddySMSService();
			break;
		default:
		}

	}

	/**
	 * SMSLANE SMS GATEWAY INTEGRATION
	 */
	private void smslaneService() {

		org.apache.commons.httpclient.HttpClient client = null;
		PostMethod post = null;
		String sURL;
		client = new org.apache.commons.httpclient.HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// set
		sURL = smsGatewayURL;
		post = new PostMethod(sURL);
		// SMS Lane
		post.addParameter("user", smsGatewayUsername);
		post.addParameter(password, smsGatewayPwd);
		post.addParameter("msisdn", number);
		post.addParameter("msg", message);
		post.addParameter("sid", smsGatewaySid);
		post.addParameter("fl", smsUniqueid);
		post.addParameter("GWID", smsGatewayGwid);
		try {
			client.executeMethod(post);
			String sent = post.getResponseBodyAsString();
			GenericLogger.info(ModuleName.SMS, this, sent);
		} catch (Exception e) {
			GenericLogger.error(ModuleName.SMS, this, e);
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * GUPSHUP SMS GATEWAY INTEGRATION
	 */
	private void gupshupSMSService() {

		org.apache.commons.httpclient.HttpClient client = null;
		PostMethod post = null;
		String sURL;
		client = new org.apache.commons.httpclient.HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// set
		sURL = smsGatewayURL;
		post = new PostMethod(sURL);

		/* GUPSHUP */
		post.addParameter("method", smsGatewayGwid);
		post.addParameter("send_to", number);
		post.addParameter("msg", message);// 3gc SETT; 123123123123123
		post.addParameter("msg_type", smsGatewaySid);
		post.addParameter("userid", smsGatewayUsername);
		post.addParameter("auth_scheme", "plain");
		post.addParameter(password, smsGatewayPwd);
		post.addParameter("&mask", maskName);
		post.addParameter("v", smsUniqueid);
		post.addParameter("format", "text");

		try {
			client.executeMethod(post);
			String sent = post.getResponseBodyAsString();
			GenericLogger.info(ModuleName.SMS, this, sent);
		} catch (Exception e) {
			GenericLogger.error(ModuleName.SMS, this, e);
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * GODADDY SMS GATEWAY INTEGRATION
	 */
	private void godaddySMSService() {

		org.apache.commons.httpclient.HttpClient client = null;
		GetMethod post = null;
		String sURL;
		client = new org.apache.commons.httpclient.HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// set
		sURL = smsGatewayURL;
		post = new GetMethod(sURL);
		// give all in string
		post.setQueryString(new NameValuePair[] { new NameValuePair("uname", smsGatewayUsername),
				new NameValuePair(password, smsGatewayPwd), new NameValuePair("sender", smsGatewaySid),
				new NameValuePair("receiver", number), new NameValuePair("route", smsGatewayGwid),
				new NameValuePair("msgtype", smsUniqueid), new NameValuePair("sms", message)

		});
		try {
			client.executeMethod(post);
			String sent = post.getResponseBodyAsString();
			GenericLogger.info(ModuleName.SMS, this, sent);
		} catch (Exception e) {
			GenericLogger.error(ModuleName.SMS, this, e);
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * ESANCHAR SMS GATEWAY INTEGRATION
	 * 
	 * @throws IOException
	 */
	private void esancharSMSService() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			JSONObject jsonMap = new JSONObject();
			jsonMap.put("UniqueID", smsUniqueid);
			jsonMap.put("serviceName", serviceName);
			jsonMap.put("language", language);
			jsonMap.put("message", message);
			jsonMap.put("mobileNo", bulknumber);
			String jsonData = jsonMap.toString();

			HttpPost request = new HttpPost(smsGatewayURL + "?client_id=" + smsGatewaySid);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json");
			request.setHeader("X-Stream", "true");
			request.addHeader("username", smsGatewayUsername);
			request.addHeader(password, smsGatewayPwd);
			request.setEntity(new StringEntity(jsonData, "UTF-8"));
			HttpResponse response = httpClient.execute(request);
			byte[] responseBody = EntityUtils.toByteArray(response.getEntity());
			String result = new String(responseBody);
			GenericLogger.info(ModuleName.SMS, this, result);
		} catch (Exception e) {
			GenericLogger.error(ModuleName.SMS, this, e);
		} finally {
			httpClient.close();
		}

	}

}
