package com.blog.crm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Author;
import com.blog.crm.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendNotification(Author author)
	{
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(author.getEmail());
		mail.setSubject("Spring Boot Mail");
		mail.setText("Spting Boot Test Mail Service");
		javaMailSender.send(mail);
	}
}
