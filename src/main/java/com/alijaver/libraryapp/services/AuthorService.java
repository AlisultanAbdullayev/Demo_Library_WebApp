package com.alijaver.libraryapp.services;

import com.alijaver.libraryapp.daos.AuthorDao;
import com.alijaver.libraryapp.models.Author;
import com.alijaver.libraryapp.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getAll(Sort sort) {
        return authorRepository.findAll(sort);
    }

    @Override
    public Author get(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        Author author;
        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
        } else {
            throw new RuntimeException("Author not found by id :: " + id);
        }
        return author;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public List<Author> search(String keyword) {
        List<Author> authors;
        if (keyword != null && !keyword.isEmpty()) {
            authors = authorRepository.findByFullNameContainingIgnoreCaseOrderByFullNameAsc(keyword);
        } else {
            authors = authorRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "fullName"));
        }
        return authors;
    }
}
