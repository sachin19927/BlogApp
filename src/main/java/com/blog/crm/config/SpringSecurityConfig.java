package com.blog.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{


	@Autowired
	private UserDetailsService userService;
	
	 //@Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	 }
	
	 @Override
	 public void configure(WebSecurity web) throws Exception {
	     web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
	 }
	 
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN") 
			//.anyRequest().authenticated()    
			.anyRequest().permitAll()    
			.and()
			.formLogin()                     
			.loginPage("/login")			
			.permitAll()					
			.and()
			.logout()						 
			.logoutSuccessUrl("/login?logout")
			.permitAll()
			;
	}

	
}
