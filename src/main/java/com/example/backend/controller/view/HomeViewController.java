package com.example.backend.controller.view;

import com.example.backend.dto.ProblemCountDto;
import com.example.backend.dto.UserDto;
import com.example.backend.model.*;
import com.example.backend.service.CommentService;
import com.example.backend.service.LeaderboardService;
import com.example.backend.service.ProblemService;
import com.example.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeViewController {

    private final UserService userService;

    private final CommentService commentService;

    private final ProblemService problemService;

    private final LeaderboardService leaderboardService;

    public HomeViewController(UserService userService, CommentService commentService,
                              ProblemService problemService, LeaderboardService leaderboardService){
        this.userService = userService;
        this.commentService = commentService;
        this.problemService = problemService;
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        if(userDetails == null){
            return "index";
        }
        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "index";
    }

    @GetMapping("/contact")
    public String contact(@AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        if(userDetails == null){
            return "contact";
        }
        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "contact";
    }

    @GetMapping("/faq")
    public String faq(@AuthenticationPrincipal UserPrincipal userDetails, Model model) {
        if(userDetails == null){
            return "faq";
        }
        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "faq";
    }

    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable long userId,  @AuthenticationPrincipal UserPrincipal userDetails,
                              Model model){
        UserDto currentUser = userService.getBasicUserInfo(userDetails.getUsername());
        UserDto targetUser = userService.getBasicUserInfo(userId);
        if(currentUser.getId() != targetUser.getId()){
            userService.viewedProfile(targetUser.getId());
        }

        int totalViews = userService.getProfileViews(targetUser.getId());
        int totalComments = commentService.getAllCommentsForUser(userId, OrderBy.NEWEST).size();
        int rank = leaderboardService.getUserRankByRating(targetUser.getRatings());
        ProblemCountDto userSolvedProblemsCount = userService.getSolvedProblems(targetUser.getId());
        ProblemCountDto totalProblemsCount = problemService.getTotalProblems();
        List<Problem> problems = problemService.getRecentSolvedProblems(targetUser.getId());
        Set<Tag> skillTags = userService.getTags(targetUser.getEmail(), TagType.SKILL).stream()
                .limit(10)
                .collect(Collectors.toSet());
        Set<Tag> languageTags = userService.getTags(targetUser.getEmail(), TagType.LANGUAGE);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("targetUser", targetUser);
        model.addAttribute("viewsCount", totalViews);
        model.addAttribute("commentsCount", totalComments);
        model.addAttribute("userProb", userSolvedProblemsCount);
        model.addAttribute("totalProb", totalProblemsCount);
        model.addAttribute("problems", problems);
        model.addAttribute("skillTags", skillTags);
        model.addAttribute("languageTags", languageTags);
        model.addAttribute("rank", rank);
        return "userprofile";
    }



}
