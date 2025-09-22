package com.example.backend.controller.view;

import com.example.backend.util.AuthUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/verification")
    public String verification(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "verification";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "forgotpassword";
    }

    @GetMapping("/resetpassword")
    public String resetPassword(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "resetpassword";
    }

}
