package com.blog.crm.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.crm.domain.Author;
import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;
import com.blog.crm.repository.AuthorRepository;
import com.blog.crm.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> list() {
		GenericLogger.info(ModuleName.AUTHOR, this, "Authors details");
		return authorRepository.findAll();
	}

	@Override
	public Author save(Author author) {
		GenericLogger.info(ModuleName.AUTHOR, this, "Authors Saving");
		GenericLogger.info(ModuleName.AUTHOR, this, author.toString());
		return authorRepository.save(author);
	}

	@Override
	public Author get(Integer id) {
		GenericLogger.info(ModuleName.AUTHOR, this, "Author based on Id");
		Optional<Author> result = authorRepository.findById(id);
		Author auhtor = null;
		if (result.isPresent()) {
			auhtor = result.get();
			GenericLogger.info(ModuleName.AUTHOR, this, "Author Exist :" + auhtor.toString());
		} else {
			GenericLogger.error(ModuleName.AUTHOR, this, "Author No data Found in this case");
			throw new RuntimeException(this.getClass() + "-- No data Found in this case");

		}
		return auhtor;
	}

	@Override
	public void delete(Integer id) {
		GenericLogger.info(ModuleName.AUTHOR, this, "Author delete base on Id");
		authorRepository.deleteById(id);
	}

	@Override
	public Author findByFirstName(String firstName) {
		GenericLogger.info(ModuleName.AUTHOR, this, "Find Author base on FirstName");
		return authorRepository.findByFirstName(firstName);
	}

}
