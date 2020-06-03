package com.blog.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.crm.service.PostService;

@Controller
public class HomeController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("post", postService.getLatestPost());
		return "index";
	}

}
