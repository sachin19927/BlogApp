package com.blog.crm.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * <pre>
 *  Multiple Lang configuration fetching from property files
 * </pre>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @Since 19-06-2020
 *
 */

@Configuration
public class LocalResolverConfig implements WebMvcConfigurer {

	// ========Default value for locale set from properties file========
	@Value("${locale.default.language}")
	private String defaultLocaleLang;

	@Value("${locale.default.country}")
	private String defaultLocaleCountry;

	@Value("${locale.message.resource.path}")
	private String resourceBundleMessagePath;

	/**
	 * Configure the resourceBundle message source
	 * 
	 * @return the source path for the message property files which are used to
	 *         support localization in the application
	 *         <p>
	 *         Read messages_xxx.properties file. For example:
	 *         messages_ka.properties
	 *         </p>
	 */
	@Bean(name = "messageSource")
	public MessageSource getMessageResource() {
		ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
		messageResource.setBasename(resourceBundleMessagePath);
		messageResource.setDefaultEncoding("UTF-8");
		messageResource.setCacheSeconds(3600);
		return messageResource;
	}

	/**
	 * <pre>
	 * AddInterceptor of Localechange Interceptor
	 * 
	 * <pre>
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * Allows change in locale of the application via the 'lang' parameter
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * Provide a LocaleResolver to determine the current Locale for the application
	 * Default Locale being set as US
	 * 
	 * @return locale resolver for the application, with default locale set as EN
	 *         and IN
	 */
	@Bean("localeResolver")
	public LocaleResolver getLocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale(defaultLocaleLang, defaultLocaleCountry));
		return localeResolver;
	}

}
