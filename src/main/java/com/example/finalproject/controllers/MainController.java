package com.example.finalproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

   @GetMapping("/contact")
    public String contactUs(){
        return "contact";
   }
}
