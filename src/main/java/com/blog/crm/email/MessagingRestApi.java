package com.blog.crm.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;
import com.blog.crm.sms.SmsEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Vema Reddy
 * @version %I%, %G%
 * @since 23-10-19
 */
@Api(tags = "Messaging Rest Api")
@RestController
@RequestMapping(value = "/api")
public class MessagingRestApi {

	/**
	 * This property gives windows file path
	 */
	@Value("${mobilefile.storage.path}")
	private String filepath;

	/**
	 * MAILCONFIG_PATH Indicates the file path where the gateway properties are
	 * configured
	 */
	@Value("${mail.config.path}")
	private String mailConfigPath;

	/**
	 * MAILCONFIG_PATH Indicates the file path where the gateway properties are
	 * configured
	 */
	@Value("${sms.config.path}")
	private String smsConfigPath;

	/**
	 * Constant variable
	 */
	private static final String GATEWAYNAME = "gatewayname";

	/**
	 * 
	 * @param emailBody -Input in the form of JSON
	 * @return String if success Mail sent Successfully else throws error msg
	 * @throws InvalidEmailException
	 * 
	 *                               <pre>
	 * {@code sample jsondata (except ccaddress all inputs are mandatory)
	 *              JSONObject  obj = new JSONObject();
	 *              if (attachmentflag=="NO") then                 
	 *             	obj.put("toaddress", "test.project@bcits.in");
					obj.put("ccaddress", "product@bcits.in");
					obj.put("mailsubject", "TEST MAIL");
					obj.put("messagecontent", "Dear ,Welcome to bcits");
					obj.put("gatewayname", "BCITS"); //gatewayname should  be in capital letters
					obj.put("attachmentflag", "NO"); 
					obj.put("sourceflag", "MOBILE"); MOBILE / WEB 
	 *              
	 *              else
	 *              
	 *              obj.put("toaddress", "test.project@bcits.in");
					obj.put("ccaddress", "product@bcits.in");
					obj.put("mailsubject", "TEST MAIL");
					obj.put("messagecontent", "Dear ,Welcome to bcits");
					obj.put("gatewayname", "BCITS"); //gatewayname should  be in capital letters
					obj.put("attachmentflag", "YES");
					obj.put("sourceflag", "MOBILE"); MOBILE / WEB 
					if(sourceflag == WEB) then 
					obj.put("attachment", List<String>); add only file paths
					else
					obj.put("attachment", Map<String,String>); 
					convert file to byte array and then encrypt using base64(import org.apache.commons.codec.binary.Base64;)
					(we can send one or more file at same time) 
	 *            }
	 * 
	 * </pre>
	 */

	@ApiOperation(value = "Sending email to users with attachment or without attachment")
	@PostMapping("/sendemail")
	public String sendEmail(@ApiParam(value = "Email body should be in JSON format "
			+ "and following is the sample input : \n\n" + "{\"toaddress\":\"dummymail@gmail.com\","
			+ "\"ccaddress\":\"testmail@gmail.com\",\r\n" + "\"mailsubject\":\"Mail subject\","
			+ "\"messagecontent\":\"msg\",\r\n" + "\"gatewayname\":\"GMAIL\",\r\n" + "\"attachmentflag\":\"YES/NO\","
			+ "\"sourceflag\":\"WEB\"} \n\n where  attachmentflag is by default 'NO' "
			+ "and if your attaching attachments then make attachmentflag is 'YES' "
			+ "and add  \"attachment\":\"[\"E:\\AMIProduct\\modules.xlsx\",\"E:\\AMIProduct\\notebook.txt\"]\" ", required = true) @RequestBody String emailBody)
			throws InvalidEmailException {
		JSONObject obj;
		try {
			obj = new JSONObject(emailBody);

			if (obj.has("attachmentflag")) {
				String toaddress = obj.getString("toaddress");
				String ccaddress = obj.getString("ccaddress");
				String messagecontent = obj.getString("messagecontent");
				String mailsubject = obj.getString("mailsubject");
				String gatewayname = obj.getString(GATEWAYNAME);
				String attachmentflag = obj.getString("attachmentflag");
				String sourceflag = obj.getString("sourceflag");
				if (attachmentflag.equalsIgnoreCase("NO")) {
					new Thread(new MailEntity(toaddress, ccaddress, mailsubject, messagecontent, gatewayname,
							mailConfigPath)).start();
				} else {
					List<String> attachment;
					if ("MOBILE".equals(sourceflag)) {
						attachment = getFilePaths(obj.getJSONObject("attachment"));
					} else {
						attachment = convertJsonArraytoList(obj.getJSONArray("attachment"));
					}
					new Thread(new MailEntity(toaddress, ccaddress, mailsubject, messagecontent, attachment,
							gatewayname, mailConfigPath)).start();
				}
				return "Mail sent Successfully";
			} else {
				return "attachmentflag is mandatory";
			}

		} catch (JSONException e) {
			GenericLogger.error(ModuleName.EMAIL, this, e);

			return e.getMessage();
		}

	}

	/**
	 * This method is used to convert JSONarray to list
	 * 
	 * @param array
	 * @return List of String
	 * @throws JSONException
	 */
	private static List<String> convertJsonArraytoList(JSONArray array) throws JSONException {
		List<String> list = new ArrayList<>();
		int i = 0;
		while (i < array.length()) {
			list.add(array.getString(i));
			i++;
		}
		return list;
	}

	/**
	 * This method is used to decrypt the encrypted files and stores into given path
	 * Files are encrypted using base64(import
	 * org.apache.commons.codec.binary.Base64)
	 * {@code byte[] bytearray=Base64.decodeBase64(file.getBytes());}
	 * 
	 * @param files - encrypted files
	 * @return List of String -if success returns List of file paths else error msg
	 */
	private List<String> getFilePaths(JSONObject jsonobj) {
		List<String> filepaths = new ArrayList<>();

		String pathName = filepath;
		Iterator<?> iterator = jsonobj.keys();
		while (iterator.hasNext()) {
			String fileName = (String) iterator.next();
			String file = null;
			try {
				file = jsonobj.getString(fileName);
				String finalfile = pathName + "" + fileName;
				byte[] bytearray = Base64.decodeBase64(file.getBytes());
				FileUtils.writeByteArrayToFile(new File(finalfile), bytearray);
				filepaths.add(finalfile);
			} catch (JSONException | IOException e) {
				GenericLogger.error(ModuleName.EMAIL, this, e);
			}

		}

		return filepaths;
	}

	/**
	 * This Service is used to send SMS to single consumer/user/other
	 * 
	 * <pre>
	 * {@code Sample JSON Input
	 *           JSONObject obj=new JSONObject();
	 *           obj.put("mobnumber", "9898454520");
	 *			 obj.put("msgcontent", "Complaint with docket number testmail.");
	 *			 obj.put("gatewayname", "GUPSHUP");//gatewayname should  be in capital letters
	 *			 obj.put("sourceflag", "WEB"); //MOBILE/WEB 
	 *        }
	 * For GUPSHUP message template is 'Complaint with docket number'
	 * </pre>
	 * 
	 * @param singleSmsBody-Input in the form of JSON
	 * @return String -if success returns "Message sent successfully" else Error msg
	 */
	@ApiOperation(value = "Sending SMS to users")
	@PostMapping("/sendSingleSMS")
	public String sendSingleSMS(
			@ApiParam(value = "Sms body should be in JSON format and following is the sample input :- \n\n"
					+ "{\"mobnumber\":\"9898454520\",\"msgcontent\":\"msg content\","
					+ "\"gatewayname\":\"ESANCHAR_HTTP\",\"sourceflag\":\"WEB\"}", required = true) @RequestBody String singleSmsBody) {
		JSONObject obj;
		try {
			obj = new JSONObject(singleSmsBody);
			if (obj.length() > 0) {
				GenericLogger.info(ModuleName.SMS, this, "SMS sending started to " + obj.getString("mobnumber"));
				new Thread(new SmsEntity(obj.getString("mobnumber"), obj.getString("msgcontent"),
						obj.getString(GATEWAYNAME), smsConfigPath)).start();
			}
			return "Message sent successfully";
		} catch (JSONException e) {
			GenericLogger.error(ModuleName.SMS, this, e);
			return e.getMessage();
		}

	}

	/**
	 * This Service is used to send SMS to one or more consumers/users/others
	 * 
	 * <pre>
	 * {@code    Sample JSON Input
	 *           JSONObject obj=new JSONObject();
	 *           List<String> mobilenumbers=new ArrayList<>();
	 *           mobilenumbers.add("9898454520");
	 *           obj.put("bulknumbers", mobilenumbers);
	 *			 obj.put("msgcontent", "test msg");
	 *			 obj.put("gatewayname", "ESANCHAR_HTTP");//gatewayname should  be in capital letters
	 *			 obj.put("sourceflag", "WEB"); //MOBILE/WEB }
	 * </pre>
	 * 
	 * @param bulkSmsBody -Input in the form of JSON
	 * @return String -if success returns "Message sent successfully" else Error msg
	 */
	@ApiOperation(value = "Sending SMS to multiple users with same message content")
	@PostMapping("/sendBulkSMS")
	public String sendBulkSMS(@ApiParam(value = "Bulk sms body should be in JSON format and "
			+ "following is the sample input :- \n\n "
			+ "{\"bulknumbers\":[\"9898424280\",\"9898424281\"],\"msgcontent\":\"msg content\","
			+ "\"gatewayname\":\"ESANCHAR_HTTP\",\"sourceflag\":\"WEB\"}", required = true) @RequestBody String bulkSmsBody) {
		JSONObject obj;
		try {
			obj = new JSONObject(bulkSmsBody);
			if (obj.length() > 0) {
				JSONArray array = obj.getJSONArray("bulknumbers");
				List<String> mobnumbers = convertJsonArraytoList(array);
				new Thread(new SmsEntity(mobnumbers, obj.getString("msgcontent"), obj.getString(GATEWAYNAME),
						smsConfigPath)).start();
			}
			return "Message sent successfully";
		} catch (JSONException e) {
			GenericLogger.error(ModuleName.SMS, this, e);
			return e.getMessage();
		}

	}

}
