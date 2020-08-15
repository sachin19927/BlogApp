package com.blog.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <h3>Swagger Config Class</h3>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 *
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

	/**
	 * <pre>
	 * Configuration of package and url patterns to be scanned
	 * Any class with '@Api' annotation will be scanned and enlisted
	 * in the 'swagger-ui.html' page
	 * and metadata info about application
	 * </pre>
	 * 
	 * @return new Docket
	 */
	@Bean
	public Docket applcationApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.blog.crm"))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).build().apiInfo(metaData());
	}

	/**
	 * For complete description of swagger integration and annotations for api
	 * documentation see below link
	 * {@link https://github.com/swagger-api/swagger-core/wiki/annotations}
	 * 
	 * @return endpoint for apis configuration
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Spring Boot REST API").description("\"Spring Boot REST API for Blog App \"")
				.version("1.0.0").license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.contact(new Contact("Sachin HS", "https://springframework.sachin/about/", "sachin19927@gmail.com"))
				.build();

	}

}
