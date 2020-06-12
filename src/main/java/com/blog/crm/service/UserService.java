package com.blog.crm.service;

import com.blog.crm.domain.User;

public interface UserService {

	public User findByEmail(String email);
	
	public User findByUserName(String email);
	
}
