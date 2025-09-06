package com.example.backend.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        return "signup";
    }

    @GetMapping("/verification")
    public String verificationPage(Model model) {
        // Dummy data for presentation/demo
        model.addAttribute("title", "Account Verification");
//        model.addAttribute("error", ""); // Empty string if no error, or a sample error message
        // Example: model.addAttribute("error", "Invalid code. Please try again.");

        return "verification"; // Thymeleaf template name (verification.html)
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {
        // Dummy data for presentation/demo
        model.addAttribute("title", "Forgot Password");
        // Example: model.addAttribute("error", "Email address not found.");

        return "forgotpassword"; // Thymeleaf template name (forgotpassword.html)
    }

}
