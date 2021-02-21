package com.alijaver.libraryapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/preview")
    public String previewPage() {
        return "preview";
    }

    @GetMapping("/team")
    public String teamPage() {
        return "team";
    }

}
