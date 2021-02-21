package com.alijaver.libraryapp.controllers;

import com.alijaver.libraryapp.models.Author;
import com.alijaver.libraryapp.models.Book;
import com.alijaver.libraryapp.models.Publisher;
import com.alijaver.libraryapp.services.AuthorService;
import com.alijaver.libraryapp.services.BookService;
import com.alijaver.libraryapp.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    @GetMapping("/books")
    public String filteredBooksList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Book> booksList = bookService.search(keyword);
        model.addAttribute("booksList", booksList);
        model.addAttribute("keyword", keyword);
        return "books";
    }

    @GetMapping("/books/new")
    public String bookAdd(Model model) {
        List<Author> authorList = authorService.getAll(Sort.by(Sort.Direction.ASC, "fullName"));
        List<Publisher> publisherList = publisherService.getAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("book", new Book());
        model.addAttribute("authorList", authorList);
        model.addAttribute("publisherList", publisherList);
        return "book_form";
    }

    @PostMapping("/books/save")
    public String bookSave(@ModelAttribute(value = "book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/update/{id}")
    public String bookUpdate(@PathVariable Long id, Model model) {
        List<Author> authorList = authorService.getAll();
        List<Publisher> publisherList = publisherService.getAll();
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        model.addAttribute("authorList", authorList);
        model.addAttribute("publisherList", publisherList);
        return "book_update";
    }

    @GetMapping("/books/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String bookDelete(@PathVariable Long id, @ModelAttribute Book book) {
        book = bookService.get(id);
        bookService.delete(book);
        return "redirect:/books";
    }
}
