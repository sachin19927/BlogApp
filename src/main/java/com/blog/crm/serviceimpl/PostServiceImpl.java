package com.blog.crm.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Post;
import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;
import com.blog.crm.repository.PostRepository;
import com.blog.crm.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public Post getLatestPost() {
		GenericLogger.info(ModuleName.POST, this, "Show Latest Post");
		return postRepository.findFirstByOrderByPostedOnDesc();
	}

	@Override
	public List<Post> list() {
		GenericLogger.info(ModuleName.POST, this, "Show All Post");
		return postRepository.findAllByOrderByPostedOnDesc();
	}

	@Override
	public Post getBySlug(String slug) {
		GenericLogger.info(ModuleName.POST, this, "find Post based on slug: " + slug);
		return postRepository.findBySlug(slug);
	}

	@Override
	public List<Post> listByAuthor(Integer id) {
		GenericLogger.info(ModuleName.POST, this, "List Post based on Author id: " + id);
		return postRepository.findAllByAuthorIdOrderByPostedOnDesc(id);
	}

	@Override
	public Post get(Integer id) {
		GenericLogger.info(ModuleName.POST, this, "find Post based on ID: " + id);
		Optional<Post> result = postRepository.findById(id);
		Post post = null;
		if (result.isPresent()) {
			GenericLogger.info(ModuleName.POST, this, "Post found based on ID: " + post.toString());
			post = result.get();
		} else {
			throw new RuntimeException(this.getClass() + "-- No data Found in this case");
		}
		return post;
	}

	@Override
	public Post save(Post post) {
		GenericLogger.info(ModuleName.POST, this, "Saving Post :" + post.toString());
		return postRepository.save(post);
	}

	@Override
	public void delete(Integer id) {
		GenericLogger.info(ModuleName.POST, this, "delete Post based on ID: " + id);
		postRepository.deleteById(id);
	}

}
