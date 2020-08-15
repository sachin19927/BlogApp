package com.blog.crm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.crm.service.AuthorService;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	private AuthorService authorService;

	@Test
	void contextLoads() {
		// fail("simple fail method");
		// absence of failure is success
	}

}
