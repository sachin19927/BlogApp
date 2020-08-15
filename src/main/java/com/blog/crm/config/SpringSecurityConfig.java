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

/**
 * <h3>Spring Security Config file</h3>
 * 
 * <pre>
 * SpringSecurityConfig extends WebSecurityConfigurerAdapter
 * EnableGlobalMethodSecurity to secure method level class level configuration
 * </pre>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userService;

	/**
	 * Password Encryption Bcrypt
	 * 
	 * @return bcrypt encrypted password with 4 rounds
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	/**
	 * Override configure(WebSecurity web) to ignore static resources
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
	}

	/**
	 * White listing URL to permit during Spring Security check
	 */
	private static final String[] AUTH_WHITELIST = {

			"/registration**", "/js/**", "/css/**", "/images/**", "/webjars/**", "/swagger-resources/**",
			"/swagger-ui.html", "/v2/api-docs"

	};

	/**
	 * Override configure(AuthenticationManagerBuilder auth)
	 * 
	 * <pre>
	 * Login Auhtenticatin of Spring Security using UserDetailsService along with Bcrypt encoder
	 * User details will be obtained from userservice
	 * </pre>
	 */
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	/**
	 * Override configure(HttpSecurity http)
	 * 
	 * <pre>
	 * Allowing whitelist paths skip role based and login authorization
	 * Providing ROLE base access
	 * Providing Login url and Logout URL
	 * </pre>
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().antMatchers("/admin/**").hasRole("ADMIN")
				// .anyRequest().authenticated()
				.anyRequest().permitAll().and().formLogin().loginPage("/login").permitAll().and().logout()
				.logoutSuccessUrl("/login?logout").permitAll();
	}

}
