package com.blog.crm.email;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.util.ResourceUtils;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;

/**
 * @see MailEntity contains mail properties and mail sending methods
 * @author Vema Reddy
 * @version %I%, %G%
 * @since 23-10-19
 */
@SuppressWarnings("serial")
public class MailEntity implements Serializable, Runnable {

	private String toAddress;

	private String gateWayUsername;

	private String gateWayPassword;

	private String mailSmtpHost;

	private String mailSmtpAuth;

	private String mailSmtpPort;

	private String fromAddress;

	private String fromName;

	private String mailSubject;

	private String messageContent;

	private List<String> attachmentPath = new ArrayList<>();

	private String ccAddress;

	private String socketFactoryPort;

	private String socketFactoryClass;

	private String gatewayName;

	private String mailConfigPath;

	/**
	 * Email Regex java
	 */
	private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

	private static final Pattern MYREGEX = Pattern.compile(EMAIL_REGEX);

	/**
	 * Parameterized constructor to send simple plan text email
	 * 
	 * @param toAddress      It represents the email id for whom need to be send
	 * @param ccAddress      Optional
	 * @param mailSubject    The mail subject
	 * @param messageContent The message content
	 * @param gatewayName    Name of the gateway
	 * @param mailConfigPath The mail configuration setting path (it is used in
	 *                       internal purpose)
	 * @throws InvalidEmailException getGatewayProperties is a method to get gateway
	 *                               properties based given path and gateway name
	 *                               validateEmail is a method to validate the input
	 *                               email address with EMAIL_REGEX pattern
	 */
	public MailEntity(String toAddress, String ccAddress, String mailSubject, String messageContent, String gatewayName,
			String mailConfigPath) throws InvalidEmailException {
		super();
		this.toAddress = toAddress;
		this.mailSubject = mailSubject;
		this.messageContent = messageContent;
		this.ccAddress = ccAddress;
		this.gatewayName = gatewayName;
		this.mailConfigPath = mailConfigPath;

		if (validateEmail(toAddress)) {
			this.getGatewayProperties(gatewayName, mailConfigPath);
		} else {
			throw new InvalidEmailException("Emailid is wrong");

		}

	}

	/**
	 * Parameterized constructor to send email with attachment
	 * 
	 * @param toAddress      It represents the email id for whom need to be send
	 * @param ccAddress      Optional
	 * @param mailSubject    The mail subject
	 * @param messageContent The message content
	 * @param attachmentPath The attached file path
	 * @param gatewayName    Name of the gateway
	 * @param mailConfigPath The mail configuration setting path (it is used in
	 *                       internal purpose)
	 * @throws InvalidEmailException getGatewayProperties is a method to get gateway
	 *                               properties based given path and gateway name
	 *                               validateEmail is a method to validate the input
	 *                               email address with EMAIL_REGEX pattern
	 */
	public MailEntity(String toAddress, String ccAddress, String mailSubject, String messageContent,
			List<String> attachmentPath, String gatewayName, String mailConfigPath) throws InvalidEmailException {
		super();
		this.toAddress = toAddress;
		this.mailSubject = mailSubject;
		this.messageContent = messageContent;
		this.attachmentPath = attachmentPath;
		this.ccAddress = ccAddress;
		this.gatewayName = gatewayName;
		this.mailConfigPath = mailConfigPath;
		if (validateEmail(toAddress)) {
			this.getGatewayProperties(gatewayName, mailConfigPath);
		} else {
			throw new InvalidEmailException("Emailid is wrong");
		}
	}

	/**
	 * Returns EMAIL
	 * 
	 * @return String
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * 
	 * @param toAddress-the EMAIL to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Returns gateway username
	 * 
	 * @return The gateway username
	 */
	public String getGateWayUsername() {
		return gateWayUsername;
	}

	/**
	 * 
	 * @param gateWayUsername The gate way user name
	 */
	public void setGateWayUsername(String gateWayUsername) {
		this.gateWayUsername = gateWayUsername;
	}

	/**
	 * 
	 * @return String
	 */
	public String getGateWayPassword() {
		return gateWayPassword;
	}

	/**
	 * 
	 * @param gateWayPassword The gatway passowrd
	 */
	public void setGateWayPassword(String gateWayPassword) {
		this.gateWayPassword = gateWayPassword;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	/**
	 * 
	 * @param mailSmtpHost The mail smtp host
	 */
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	/**
	 * 
	 * @param mailSmtpAuth The mail smtp authentication
	 */
	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	/**
	 * 
	 * @param mailSmtpPort The smtp port
	 */
	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	/**
	 * 
	 * @return the from mail address
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * 
	 * @param fromAddress the from mail address
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * 
	 * @return String
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * 
	 * @param fromName The name of the user
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * 
	 * @param mailSubject The mail subject
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * 
	 * @return String
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * 
	 * @param messageContent The message content
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * 
	 * @return the list of string file paths
	 */
	public List<String> getAttachmentPath() {
		return attachmentPath;
	}

	/**
	 * 
	 * @param attachmentPath The attched file path
	 */
	public void setAttachmentPath(List<String> attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	/**
	 * 
	 * @return String
	 */
	public String getCcAddress() {
		return ccAddress;
	}

	/**
	 * 
	 * @param ccAddress The copy address
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSocketFactoryPort() {
		return socketFactoryPort;
	}

	/**
	 * 
	 * @param socketFactoryPort The socketFactoryPort
	 */
	public void setSocketFactoryPort(String socketFactoryPort) {
		this.socketFactoryPort = socketFactoryPort;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}

	/**
	 * 
	 * @param socketFactoryClass The socketFactoryClass
	 */
	public void setSocketFactoryClass(String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}

	/**
	 * 
	 * @return String
	 */
	public String getGatewayName() {
		return gatewayName;
	}

	/**
	 * 
	 * @param gatewayName Name of the gateway
	 */
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getMailConfigPath() {
		return mailConfigPath;
	}

	public void setMailConfigPath(String mailConfigPath) {
		this.mailConfigPath = mailConfigPath;
	}

	/**
	 * This method validates the input email address with EMAIL_REGEX pattern
	 * 
	 * @param toAddress - email
	 * @return boolean - if success true else false
	 */
	public static boolean validateEmail(String toAddress) {
		return MYREGEX.matcher(toAddress).matches();
	}

	/**
	 * This method provide the email gateway properties
	 * 
	 * @param gatewayName    - it is used to identify the email gateway
	 * @param mailConfigPath The mail configuration details
	 */
	public final void getGatewayProperties(String gatewayName, String mailConfigPath) {
		File file = null;
		try {
			file = ResourceUtils.getFile(mailConfigPath);

			// Read File Content
			String content = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

			JSONObject obj = new JSONObject(content);
			if (obj.length() > 0) {
				JSONObject json = obj.getJSONObject(gatewayName);
				this.mailSmtpHost = json.getString("MAIL.SMTP.HOST");
				this.mailSmtpPort = json.getString("MAIL.SMTP.PORT");
				this.gateWayUsername = json.getString("MAIL.GATEWAY.USERNAME");
				this.gateWayPassword = json.getString("MAIL.GATEWAY.PASSWORD");
				this.fromAddress = json.getString("MAIL.GATEWAY.FROMADDRESS");
				this.fromName = json.getString("MAIL.GATEWAY.FROMNAME");
				this.mailSmtpAuth = json.getString("MAIL.SMTP.AUTH");
				this.socketFactoryPort = json.getString("MAIL.SMTP.SOCKETFACTORY.PORT");
				this.socketFactoryClass = json.getString("MAIL.SMTP.SOCKETFACTORY.CLASS");
			}

		} catch (IOException | JSONException e) {
			GenericLogger.error(ModuleName.EMAIL, this, e);

		}
	}

	/**
	 * this method is used to send multiple mails simultaneously based on gateways
	 */
	@Override
	public void run() {
		GenericLogger.info(ModuleName.EMAIL, this, "run method getting called and sending email to : " + toAddress);
		try {
			Properties props = System.getProperties();

			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", mailSmtpPort);

			if (socketFactoryPort != null && !("".equals(socketFactoryPort))) {
				props.put("mail.smtp.socketFactory.port", socketFactoryPort);
			}
			if (socketFactoryClass != null && !("".equals(socketFactoryClass))) {
				props.put("mail.smtp.socketFactory.class", socketFactoryClass);
			}

			props.put("mail.smtp.starttls.enable", mailSmtpAuth);
			props.put("mail.smtp.auth", mailSmtpAuth);

			Session session = Session.getDefaultInstance(props);

			Message message = new MimeMessage(session);

			message.setContent(messageContent, "text/html");
			message.setSubject(mailSubject);
			message.setFrom(new InternetAddress(fromAddress, fromName));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			if (ccAddress != null) {
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));
			}

			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(messageContent, "text/html");

			MimeMultipart multipart = new MimeMultipart("mixed");
			multipart.addBodyPart(htmlPart);
			System.out.println(attachmentPath);
			if (!attachmentPath.isEmpty()) {
				for (String file : attachmentPath) {
					// Define the attachment
					MimeBodyPart att = new MimeBodyPart();
					DataSource fds = new FileDataSource(file);
					att.setDataHandler(new DataHandler(fds));
					att.setFileName(fds.getName());
					htmlPart.setDisposition(Part.INLINE);
					multipart.addBodyPart(att);
				}

			}

			Transport transport = session.getTransport();
			transport.connect(mailSmtpHost, gateWayUsername, gateWayPassword);
			message.setContent(multipart);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			GenericLogger.info(ModuleName.EMAIL, this, "Email Sent to " + toAddress);
		} catch (MessagingException | UnsupportedEncodingException e) {
			GenericLogger.error(ModuleName.EMAIL, this, e);
		}

	}

}
