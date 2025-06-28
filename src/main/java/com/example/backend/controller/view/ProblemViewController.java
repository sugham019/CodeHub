package com.example.backend.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProblemViewController {

    @GetMapping("/problems")
    public String problems(Model model) {
        return "problems";
    }

}
