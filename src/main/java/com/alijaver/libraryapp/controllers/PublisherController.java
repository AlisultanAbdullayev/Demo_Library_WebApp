package com.alijaver.libraryapp.controllers;

import com.alijaver.libraryapp.models.Publisher;
import com.alijaver.libraryapp.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/publishers")
    public String publishersList(@Param(value = "keyword") String keyword, Model model) {
        List<Publisher> publishersList = publisherService.search(keyword);
        model.addAttribute("publishersList", publishersList);
        model.addAttribute("keyword", keyword);
        return "publishers";

    }

    @GetMapping("/publishers/new")
    public String showPublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publisher_form";
    }

    @PostMapping("/publishers/save")
    public String savePublisher(@ModelAttribute(name = "publisher") Publisher publisher) {
        publisherService.save(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/publishers/update/{id}")
    public String updatePublisher(@PathVariable(value = "id") Long id, Model model) {
        Publisher publisher = publisherService.get(id);
        model.addAttribute("publisher", publisher);
        return "publisher_update";
    }

    @GetMapping("/publishers/delete/{id}")
    public String deletePublisher(@PathVariable(value = "id") Long id, Publisher publisher) {
        publisher = publisherService.get(id);
        publisherService.delete(publisher);
        return "redirect:/publishers";
    }
}