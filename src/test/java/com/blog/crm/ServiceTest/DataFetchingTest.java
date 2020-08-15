package com.blog.crm.ServiceTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.crm.domain.Author;
import com.blog.crm.service.AuthorService;

public class DataFetchingTest {

	@Autowired
	private AuthorService authorService;

	@Test
	public void getAutorDetailsMailByName() {
		Author author = authorService.findByFirstName("Sachin");
		System.err.println(author.getEmail());
		Author authorExpt = null;
		assertEquals(authorExpt, author);
	}

}
