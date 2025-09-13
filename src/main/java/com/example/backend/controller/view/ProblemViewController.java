package com.example.backend.controller.view;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Language;
import com.example.backend.model.Problem;
import com.example.backend.model.User;
import com.example.backend.model.UserPrincipal;
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
        if(recentProblemId == null){
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

        userService.setRecentProblemId(userDetails.getUsername(), problem.getId());

        model.addAttribute("title", problem.getTitle());
        model.addAttribute("problem", problem);
        model.addAttribute("currentUser", userDto);
        model.addAttribute("templateCode", templateCode);
        return "submit_solution";
    }

    @GetMapping("/explore")
    public String problems(@AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        List<Problem> problems = problemService.getAllProblems();
        List<UserDto> topUsers = leaderboardService.getTopRankers(10);
        UserDto userDto = userService.getBasicUserInfo(userDetails.getUsername());

        model.addAttribute("problems", problems);
        model.addAttribute("topUsers", topUsers);
        model.addAttribute("currentUser", userDto);
        return "question";
    }
}
