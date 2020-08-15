package com.blog.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <h3>Web Servlet Config</h3>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 */
@Configuration
public class WebServletConfig extends WebMvcConfigurerAdapter {

	/**
	 * Override addViewControllers to register url and form path
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/login").setViewName("auth/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}