package com.alijaver.libraryapp.controllers;

import com.alijaver.libraryapp.models.Author;
import com.alijaver.libraryapp.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String filterAuthors(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Author> authorsList = authorService.search(keyword);
        model.addAttribute("authorsList", authorsList);
        model.addAttribute("keyword", keyword);
        return "authors";
    }

    @GetMapping("/authors/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author_form";
    }

    @PostMapping("/authors/save")
    public String saveAuthor(@ModelAttribute(name = "author") Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/update/{id}")
    public String updateAuthor(@PathVariable(value = "id") Long id, Model model) {
        Author author = authorService.get(id);
        model.addAttribute("author", author);
        return "author_update";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable(value = "id") Long id) {
        Author author = authorService.get(id);
        authorService.delete(author);
        return "redirect:/authors";
    }

}
