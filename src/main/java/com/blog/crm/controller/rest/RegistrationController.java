package com.blog.crm.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.crm.domain.Author;
import com.blog.crm.service.AuthorService;
import com.blog.crm.service.NotificationService;

@RestController
public class RegistrationController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private AuthorService auhtorService;
	
	
	
	@GetMapping("/signup")
	public String signup()
	{
		return "Please Sign Up for our Service";
	}
	
	@GetMapping("/signup-success")
	public String signupSuccess()
	{
		Author author=auhtorService.findByFirstName("Sachin");
		System.err.println(author.getEmail());
		try {
			notificationService.sendNotification(author);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "Thank You For registering with us";
	}
}
