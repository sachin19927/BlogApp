package com.blog.crm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.blog.crm.domain.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
	
	Post findFirstByOrderByPostedOnDesc();
	
	List<Post> findAllByOrderByPostedOnDesc();
	
	Post findBySlug(String slug);
	
	List<Post> findAllByAuthorIdOrderByPostedOnDesc(Integer id);
	
}
