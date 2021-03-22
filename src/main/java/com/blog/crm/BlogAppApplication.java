package com.blog.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;

/**
 * <h3>Spring Boot Starter class</h3>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 * @see @EnableScheduling to allow schduling configuration in project
 */

@SpringBootApplication
@EnableScheduling
public class BlogAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlogAppApplication.class);
	}

	/**
	 * <p>
	 * Starts the Spring ApplicationContext
	 * </p>
	 * 
	 * @param args accepts command line arguments
	 */
	public static void main(String[] args) {
		GenericLogger.debug(ModuleName.LOGGER, BlogAppApplication.class,
				"Blog App Load has been started: new Terminal");
		SpringApplication.run(BlogAppApplication.class, args);
		GenericLogger.debug(ModuleName.LOGGER, BlogAppApplication.class, "Blog App load is completed: old one");
	}

}
