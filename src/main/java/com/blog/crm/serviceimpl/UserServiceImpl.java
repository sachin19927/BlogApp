package com.blog.crm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.User;
import com.blog.crm.repository.UserRepository;
import com.blog.crm.service.UserService;



@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByUserName(String email) {
		return userRepository.findByUserName(email);
	}

	// default method callled from SpringSecurity if UserDetailsService this is used has Authentication
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//User user=userRepository.findByEmail(username);
		User user=userRepository.findByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException(username);
		}
		return new UserDetaiIsImpl(user);
	}

}
