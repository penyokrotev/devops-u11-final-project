package com.example.finalproject.controllers;

import com.example.finalproject.components.RegistrationSpringSecurity;
import com.example.finalproject.models.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private static final String EMAIL_ALREADY_IN_USE_EM = "This email is already in use!";
    private static final String EMAIL_PASSWORD_EM = "Email and password should be valid!";
    private static final String VERIFICATION_EMAIL_SENT = "A verification email has been sent to: %s";
    private static final String ACCOUNT_VERIFIED = "Congratulations! Your account has been activated and email is verified!";

    private RegistrationSpringSecurity registrationSpringSecurity;

    @Autowired
    public RegistrationController(RegistrationSpringSecurity registrationSpringSecurity) {
        this.registrationSpringSecurity = registrationSpringSecurity;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userReg", new UserReg());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserReg userReg, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", EMAIL_PASSWORD_EM);
            return "register";
        }

        if (registrationSpringSecurity.checkIfEmailIsAlreadyRegistered(userReg.getEmail())) {
            model.addAttribute("error", EMAIL_ALREADY_IN_USE_EM);
            return "register";
        }
        try {
            registrationSpringSecurity.registerUser(userReg);
            model.addAttribute("success", String.format(VERIFICATION_EMAIL_SENT, userReg.getEmail()));
            return "register";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

        @GetMapping("/verification/confirm-account")
        public String confirmUserAccount (Model model, @RequestParam("token") String confirmationToken) {
            try {
                registrationSpringSecurity.confirmAccount(confirmationToken);
                model.addAttribute("success", ACCOUNT_VERIFIED);
                return "registrationVer";
            } catch (RuntimeException ex) {
                model.addAttribute("error", ex.getMessage());
                return "registrationVer";
        }
    }
}
