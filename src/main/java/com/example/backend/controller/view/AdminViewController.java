package com.example.backend.controller.view;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Problem;
import com.example.backend.model.UserPrincipal;
import com.example.backend.service.CommentService;
import com.example.backend.service.ProblemService;
import com.example.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminViewController {

    private final UserService userService;

    private final ProblemService problemService;

    public AdminViewController(UserService userService, CommentService commentService,
                              ProblemService problemService){
        this.userService = userService;
        this.problemService = problemService;
    }

    @GetMapping("/admin")
    public String adminPanel(@RequestParam(required = false) String searchTerm, @AuthenticationPrincipal UserPrincipal userDetails,
                             Model model) {
        List<Problem> problems = (searchTerm != null) ? problemService.searchProblemsByTitle(searchTerm)
                : problemService.getAllProblems();

        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("problems", problems);
        return "admin";
    }

}
