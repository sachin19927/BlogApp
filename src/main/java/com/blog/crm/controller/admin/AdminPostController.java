package com.blog.crm.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.crm.domain.Author;
import com.blog.crm.domain.Post;
import com.blog.crm.service.AuthorService;
import com.blog.crm.service.PostService;

@Controller
@Secured({"ROLE_ADMIN"})
public class AdminPostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/admin/posts")
	public String list(Model model)
	{
		model.addAttribute("posts", postService.list());
		return "admin/post/list"; 
	}

	@GetMapping("/admin/post/{id}")
	public String view(@PathVariable("id") int id,Model model)
	{
		model.addAttribute("post", postService.get(id));
		return "admin/post/view"; 
	}
	
	// CREATE | SAVE
	@GetMapping("/admin/post/create")
	public String create(Model model)
	{
		model.addAttribute("post", new Post());
		model.addAttribute("authors", authorService.list());
		return "admin/post/postForm"; 
	}
	
	
	@PostMapping("/admin/post/create")
	public String save(@Valid Post post,BindingResult bindingResult,Model model)
	{
		if(bindingResult.hasErrors())
		{
			model.addAttribute("authors", authorService.list());
			return "admin/post/postForm"; 
		}
		else 
		{
			Post savedPost=postService.save(post);
			return "redirect:/admin/post/"+savedPost.getId(); 
		}
		
	}
	
	@GetMapping("/admin/post/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("post", postService.get(id));
		model.addAttribute("authors", authorService.list());
		return "admin/post/postForm"; 
	}
	
	@GetMapping("/admin/post/delete/{id}")
	public String delete(@PathVariable("id") int id,RedirectAttributes redirectAttributes)
	{
		postService.delete(id);
		redirectAttributes.addFlashAttribute("message", "Post is deleted");
		return "redirect:/admin/posts"; 
	}
	
	
	@GetMapping("/admin/authors")
	public String listAuthors(Model model)
	{
		model.addAttribute("authors", authorService.list());
		return "admin/author/list"; 
	}

	// CREATE | SAVE
	@GetMapping("/admin/author/create")
	public String createAuhtor(Model model)
	{
		model.addAttribute("author", new Author());
		return "admin/author/authorForm"; 
	}
	

	@PostMapping("/admin/author/create")
	public String save(@Valid Author author,BindingResult bindingResult,Model model)
	{
		if(bindingResult.hasErrors())
		{
			return "admin/author/authorForm"; 
		}
		else 
		{
			Author savedAuthor=authorService.save(author);
			return "redirect:/admin/authors"; 
		}
		
	}
	
	@GetMapping("/admin/author/edit/{id}")
	public String editAuthor(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("author", authorService.get(id));
		return "admin/author/authorForm"; 
	}
	
	@GetMapping("/admin/author/delete/{id}")
	public String deleteAuthor(@PathVariable("id") int id,RedirectAttributes redirectAttributes)
	{
		authorService.delete(id);
		redirectAttributes.addFlashAttribute("message", "Author is deleted");
		return "redirect:/admin/authors"; 
	}
}
