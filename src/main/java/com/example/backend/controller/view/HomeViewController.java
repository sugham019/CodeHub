package com.example.backend.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("currentUser", "Soham");
        return "index";
    }

}
