package com.blog.crm.service;

import com.blog.crm.domain.Language;

public interface LanguageService {

	Language findByKeyAndLocale(String key, String locale);
}
