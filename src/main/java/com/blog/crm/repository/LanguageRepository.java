package com.blog.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.crm.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Language findByKeyAndLocale(String key, String locale);

}
