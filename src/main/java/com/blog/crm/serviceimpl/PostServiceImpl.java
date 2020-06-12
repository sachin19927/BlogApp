package com.blog.crm.serviceimpl;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Post get(Integer id) {
		
		Optional<Post> result = postRepository.findById(id);
		Post post=null;
		if(result.isPresent())
		{
			post=result.get();
		}
		else
		{
			throw new RuntimeException(this.getClass()+"-- No data Found in this case");
		}
		return post;
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void delete(Integer id) {
		postRepository.deleteById(id);
	}

}
