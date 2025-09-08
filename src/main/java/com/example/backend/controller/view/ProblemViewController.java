package com.example.backend.controller.view;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Leaderboard;
import com.example.backend.model.Problem;
import com.example.backend.service.LeaderboardService;
import com.example.backend.service.ProblemService;
import com.example.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

class TestCaseResult {
    private String status;
    private double time;
    private int memory;

    public TestCaseResult(String status, double time, int memory) {
        this.status = status;
        this.time = time;
        this.memory = memory;
    }

    public String getStatus() { return status; }
    public double getTime() { return time; }
    public int getMemory() { return memory; }
}



@Controller
public class ProblemViewController {


    private final ProblemService problemService;

    private final LeaderboardService leaderboardService;

    private final UserService userService;

    public ProblemViewController(UserService userService, ProblemService problemService, LeaderboardService leaderboardService){
        this.userService = userService;
        this.problemService = problemService;
        this.leaderboardService = leaderboardService;
    }

//    @GetMapping("/problem/{slug}")
//    public String editor(@PathVariable String slug, Model model) {
//        // Dummy problem info for "Reverse a String"
//        Problem problem = new Problem(
//                slug,
//                "Reverse a String",
//                "Given a string, return the reversed string.",
//                "A single line containing the string to reverse.",
//                "hello world",
//                "The reversed string",
//                "dlrow olleh",
//                "1 <= length of string <= 1000"
//        );
//
//        // Dummy test case results (normally populated after submission)
//        List<TestCaseResult> testCaseResults = Arrays.asList(
//                new TestCaseResult("Accepted", 0.010, 512),
//                new TestCaseResult("Accepted", 0.012, 520)
//        );
//
//        // Add attributes for Thymeleaf
//        model.addAttribute("title", problem.getTitle());
//        model.addAttribute("problem", problem);
//        model.addAttribute("testCaseResults", testCaseResults); // updated camelCase for template
//
//        // Example error message (could be null if no error)
//        model.addAttribute("error", null);
//
//        return "submit_solution"; // Thymeleaf template name
//    }

    @GetMapping("/explore")
    public String problems(Model model, Authentication authentication) {
        List<Problem> problems = problemService.getAllProblems();
        List<UserDto> topUsers = leaderboardService.getTopRankers(10);
        UserDto userDto = userService.getBasicUserInfo(authentication.getName());

        model.addAttribute("problems", problems);
        model.addAttribute("topUsers", topUsers);
        model.addAttribute("currentUser", userDto);
        return "question";
    }
}
