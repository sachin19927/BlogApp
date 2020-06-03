package com.blog.crm.repository;

import org.springframework.data.repository.CrudRepository;

import com.blog.crm.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
