package com.blog.crm.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Author;
import com.blog.crm.repository.AuthorRepository;
import com.blog.crm.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public List<Author> list() {
		return authorRepository.findAll();
	}

	@Override
	public Author save(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author get(Integer id) {
		Optional<Author> result = authorRepository.findById(id);
		Author auhtor=null;
		if(result.isPresent())
		{
			auhtor=result.get();
		}
		else
		{
			throw new RuntimeException(this.getClass()+"-- No data Found in this case");
		}
		return auhtor;
	}

	@Override
	public void delete(Integer id) {
		authorRepository.deleteById(id);
	}

	@Override
	public Author findByFirstName(String firstName) {
		return authorRepository.findByFirstName(firstName);
	}

	
}
