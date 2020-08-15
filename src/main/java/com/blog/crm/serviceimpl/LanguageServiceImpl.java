package com.blog.crm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Language;
import com.blog.crm.repository.LanguageRepository;
import com.blog.crm.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public Language findByKeyAndLocale(String key, String locale) {
		return languageRepository.findByKeyAndLocale(key, locale);
	}

}
