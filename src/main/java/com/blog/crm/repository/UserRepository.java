package com.blog.crm.repository;

import org.springframework.data.repository.CrudRepository;

import com.blog.crm.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserName(String email);
	
	User findByEmail(String email);

	

}
