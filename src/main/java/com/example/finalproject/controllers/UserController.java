package com.example.finalproject.controllers;

import com.example.finalproject.exceptions.UserDoesntExistException;
import com.example.finalproject.models.User;
import com.example.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myprofile")
    public String showProfilePage(Model model) {
        try {
            model.addAttribute(userService.getCurrentUser());
            return "userProfileV2";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }


    @GetMapping("/myprofile/edit")
    public String getByName(Model model, @PathVariable int id) {
        try {
            User user = userService.getById(id);
            model.addAttribute("user", user);
            return "userProfileV2";
        } catch (UserDoesntExistException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }

    @PutMapping("/myprofile/edit")
    public String update(Model model, @RequestBody @Valid User user) {
        try {
            userService.update(user);
            model.addAttribute("userProfile", user);
            return "userProfileV2";
        } catch (RuntimeException ex) {
            model.addAttribute(userService.getCurrentUser());
            model.addAttribute("error", ex.getMessage());
            return "userProfileV2";
        }
    }
}
