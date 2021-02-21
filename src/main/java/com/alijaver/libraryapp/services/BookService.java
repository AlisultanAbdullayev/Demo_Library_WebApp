package com.alijaver.libraryapp.services;

import com.alijaver.libraryapp.daos.BookDao;
import com.alijaver.libraryapp.models.Book;
import com.alijaver.libraryapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService implements BookDao {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Book get(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        } else {
            throw new RuntimeException("Book not found by id :: " + id);
        }
        return book;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> search(String keyword) {
        List<Book> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.findByNameOrBySeriNameOrAuthorOrByIsbn(keyword);
        } else {
            books = bookRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "name"));
        }
        return books;
    }
}
