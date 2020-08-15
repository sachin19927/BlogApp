package com.blog.crm.config;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.AbstractMessageSource;

import com.blog.crm.domain.Language;
import com.blog.crm.service.LanguageService;

/**
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since
 * @see Mulitple Language/Locale fething lang from DB refering Language Table
 * @see @Component("messageSource") to bind this class with and also to override
 *      MessageFormat by extending class AbstractMessageSource
 * 
 */
//@Component("messageSource")
public class LocaleResolverConfigDB extends AbstractMessageSource {

	@Autowired
	private LanguageService languageService;

	@Value("${locale.default.language}")
	private String defaultLocaleLang;

	/**
	 * Override resolveCode method to written MessageFormat by passing
	 * 
	 * @param key    key name which holds value
	 * @param locale type of lang locale
	 * @see values will be obtained based on the param and messageFormate will be
	 *      returned
	 */
	@Override
	protected MessageFormat resolveCode(String key, Locale locale) {
		Language message = languageService.findByKeyAndLocale(key, locale.getLanguage());
		if (message == null) {
			message = languageService.findByKeyAndLocale(key, defaultLocaleLang);
		}
		return new MessageFormat(message.getContent(), locale);
	}

}
