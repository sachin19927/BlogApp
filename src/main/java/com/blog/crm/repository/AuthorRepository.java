package com.blog.crm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.blog.crm.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

	List<Author> findAll();
	
	Author findByFirstName(String firstName);
	
}
