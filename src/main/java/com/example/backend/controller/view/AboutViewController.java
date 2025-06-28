package com.example.backend.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutViewController {

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

}
