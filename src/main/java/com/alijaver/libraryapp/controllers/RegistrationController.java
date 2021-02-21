package com.alijaver.libraryapp.controllers;

import com.alijaver.libraryapp.configs.PasswordConfig;
import com.alijaver.libraryapp.models.Role;
import com.alijaver.libraryapp.models.User;
import com.alijaver.libraryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;
    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.passwordConfig = passwordConfig;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user, Model model) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("existedUsername", "The username has already been used!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

}
