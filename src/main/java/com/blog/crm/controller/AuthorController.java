package com.blog.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.crm.service.AuthorService;
import com.blog.crm.service.PostService;

@Controller
@RequestMapping("/authors")
public class AuthorController {

	
	@Autowired
	private PostService postService;
	
	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/list")
	public String listAuthors(Model model)
	{
		model.addAttribute("authors", authorService.list());
		return "author/list"; 
	}
	
}
