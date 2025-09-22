package com.example.backend.controller.view;

import com.example.backend.dto.UserDto;
import com.example.backend.model.UserPrincipal;
import com.example.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesViewController {

    private final UserService userService;

    public ResourcesViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/resources")
    public String resources(@AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "resources";
    }

}