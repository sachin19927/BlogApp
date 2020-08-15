package com.blog.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.crm.service.PostService;

/**
 * <h3>Post Related URL Mapping</h3> {@link link#Posts /blogposts}
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 */
@Controller
@RequestMapping("/blogposts")
public class PostController {

	/**
	 * Autowired Post Service
	 */
	@Autowired
	private PostService postService;

	@GetMapping("/list")
	public String home(Model model) {
		model.addAttribute("posts", postService.list());
		return "post/list";
	}

	@GetMapping("/view/{slug}")
	public String view(@PathVariable("slug") String slug, Model model) {
		model.addAttribute("post", postService.getBySlug(slug));
		return "post/view";
	}

	@GetMapping("/byAuthor/{id}")
	public String byAuthor(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("posts", postService.listByAuthor(id));
		return "post/list";
	}
}
