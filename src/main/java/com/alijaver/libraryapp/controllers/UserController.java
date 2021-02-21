package com.alijaver.libraryapp.controllers;

import com.alijaver.libraryapp.models.Role;
import com.alijaver.libraryapp.models.User;
import com.alijaver.libraryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user_list";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        userRepository.findByUsername(user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "user_edit";
    }

    @PostMapping
    public String userSave(@ModelAttribute User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        user.setActive(true);
        user.setPassword(optionalUser.get().getPassword());
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/delete/{user}")
    public String userDelete(@PathVariable(name = "user") User user) {
        userRepository.delete(user);
        return "redirect:/user";
    }

}
