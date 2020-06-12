package com.blog.crm.service;

import java.util.List;

import javax.validation.Valid;

import com.blog.crm.domain.Author;
import com.blog.crm.domain.Post;

public interface AuthorService {
	
	List<Author> list();

	public Author save(Author author);
	
	public Author get(Integer id);
	
	public void delete(Integer id);

	public Author findByFirstName(String firstName);
}
