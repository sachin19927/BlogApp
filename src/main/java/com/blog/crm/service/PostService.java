package com.blog.crm.service;

import java.util.List;

import com.blog.crm.domain.Post;

public interface PostService {
	
	public Post getLatestPost();

	public List<Post> list();
	
	public Post getBySlug(String slug);

	public List<Post> listByAuthor(Integer id);
	
	public Post get(Integer id);
	
	public Post save(Post post);
	
	public void delete(Integer id);
	
}
