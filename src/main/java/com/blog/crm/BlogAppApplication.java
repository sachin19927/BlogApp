package com.blog.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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
public class BlogAppApplication {

	/**
	 * <p>
	 * Starts the Spring ApplicationContext
	 * </p>
	 * 
	 * @param args accepts command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
