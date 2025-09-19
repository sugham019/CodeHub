package com.example.backend.controller.api;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Comment;
import com.example.backend.model.OrderBy;
import com.example.backend.model.Problem;
import com.example.backend.model.UserPrincipal;
import com.example.backend.service.CommentService;
import com.example.backend.service.ProblemService;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ProblemService problemService;

    public CommentController(CommentService commentService, UserService userService, ProblemService problemService){
        this.commentService = commentService;
        this.userService = userService;
        this.problemService = problemService;
    }

    @PostMapping("/post")
    public ResponseEntity<Comment> postComment(@RequestParam String problemId, @RequestBody String content,
                                               @AuthenticationPrincipal UserPrincipal user){
        UserDto userInfo = userService.getBasicUserInfo(user.getUsername());
        Problem problem = problemService.getProblemById(problemId);
        Comment comment = commentService.postComment(userInfo, problem, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> postComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllByProblem")
    public ResponseEntity<List<Comment>> getComments(@RequestParam String problemId, @RequestParam OrderBy orderBy){
        List<Comment> comments = commentService.getAllCommentsForProblem(problemId, orderBy);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<Comment>> getComments(@RequestParam long userId, @RequestParam OrderBy orderBy){
        List<Comment> comments = commentService.getAllCommentsForUser(userId, orderBy);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
