package com.example.backend.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "signup";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {
        // Dummy data for presentation/demo
        model.addAttribute("title", "Forgot Password");
        // Example: model.addAttribute("error", "Email address not found.");

        return "forgotpassword"; // Thymeleaf template name (forgotpassword.html)
    }

}
