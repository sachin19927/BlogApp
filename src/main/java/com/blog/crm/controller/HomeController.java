package com.blog.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;
import com.blog.crm.service.PostService;

/**
 * <h3>Indexpage URL Mapping</h3> {@link link#Index /}
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 */
@Controller
public class HomeController {

	/**
	 * Autowired Post Service
	 */
	@Autowired
	private PostService postService;

	/**
	 * 
	 * @param model adding post lists
	 * @return view page
	 */
	@GetMapping("/")
	public String home(Model model) {
		GenericLogger.info(ModuleName.POST, this, "Getting List of Authors");
		model.addAttribute("post", postService.getLatestPost());
		return "index";
	}

}
