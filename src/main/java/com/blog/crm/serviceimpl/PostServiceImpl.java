package com.blog.crm.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Post;
import com.blog.crm.repository.PostRepository;
import com.blog.crm.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired 
	private PostRepository postRepository;
	
	@Override
	public Post getLatestPost() {
		return postRepository.findFirstByOrderByPostedOnDesc();
	}

	@Override
	public List<Post> list() {
		return postRepository.findAllByOrderByPostedOnDesc();
	}

	@Override
	public Post getBySlug(String slug) {
		return postRepository.findBySlug(slug);
	}

	@Override
	public List<Post> listByAuthor(Integer id) {
		return postRepository.findAllByAuthorIdOrderByPostedOnDesc(id);
	}

}
