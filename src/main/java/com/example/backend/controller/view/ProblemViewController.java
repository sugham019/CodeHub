package com.example.backend.controller.view;

import com.example.backend.dto.UserDto;
import com.example.backend.model.*;
import com.example.backend.service.LeaderboardService;
import com.example.backend.service.ProblemService;
import com.example.backend.service.TemplateService;
import com.example.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
public class ProblemViewController {


    private final ProblemService problemService;

    private final TemplateService templateService;

    private final LeaderboardService leaderboardService;

    private final UserService userService;

    public ProblemViewController(UserService userService, ProblemService problemService,
                                 LeaderboardService leaderboardService, TemplateService templateService){
        this.userService = userService;
        this.problemService = problemService;
        this.leaderboardService = leaderboardService;
        this.templateService = templateService;
    }


    @GetMapping("/problem")
    public String editor(@AuthenticationPrincipal UserPrincipal userDetails) {
        String recentProblemId = userService.getBasicUserInfo(userDetails.getUsername()).getRecentProblemId();

        if(recentProblemId == null || !problemService.problemExists(recentProblemId)){
            recentProblemId = problemService.getRandomProblem().getId();
        }
        return "redirect:/problem/" + recentProblemId;
    }

    @GetMapping("/problem/{slug}")
    public String editor(@PathVariable String slug, @RequestParam(name = "language", defaultValue = "JAVA") String language,
                         @AuthenticationPrincipal UserPrincipal userDetails, Model model) {

        Problem problem = problemService.getProblemById(slug);
        Language editorLanguage = Language.valueOf(language);
        UserDto userDto = userService.getBasicUserInfo(userDetails.getUsername());

        String templateCode = templateService.generateTemplateCode(problem.getInputType(), problem.getOutputType(), editorLanguage);
        String[] tags = problemService.getSkills(problem.getId());

        userService.setRecentProblemId(userDetails.getUsername(), problem.getId());

        model.addAttribute("title", problem.getTitle());
        model.addAttribute("problem", problem);
        model.addAttribute("currentUser", userDto);
        model.addAttribute("templateCode", templateCode);
        model.addAttribute("tags", tags);
        return "submit_solution";
    }

    @GetMapping("/explore")
    public String problems(@RequestParam(required = false) String searchTerm, @AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        List<Problem> problems = (searchTerm != null) ? problemService.searchProblemsByTitle(searchTerm)
                : problemService.getAllProblems();

        List<UserDto> topUsers = leaderboardService.getTopRankers(10);
        UserDto userDto = userService.getBasicUserInfo(userDetails.getUsername());

        model.addAttribute("problems", problems);
        model.addAttribute("topUsers", topUsers);
        model.addAttribute("currentUser", userDto);
        return "question";
    }

}
