package com.blog.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;
import com.blog.crm.service.AuthorService;

/**
 * <h3>Author Related URL Mapping</h3> {@link link#Authors /authors}
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 * 
 */
@Controller
@RequestMapping("/authors")
public class AuthorController {

	/**
	 * Autowired Author Service
	 */
	@Autowired
	private AuthorService authorService;

	/**
	 * 
	 * @param model adding list of authors
	 * @return View page
	 */
	@GetMapping("/list")
	public String listAuthors(Model model) {
		GenericLogger.info(ModuleName.LOGGER, this, "Getting List of Authors");
		model.addAttribute("authors", authorService.list());
		GenericLogger.info(ModuleName.LOGGER, this, authorService.list().toString());
		return "author/list";
	}

}
