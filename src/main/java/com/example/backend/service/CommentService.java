package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Comment;
import com.example.backend.model.OrderBy;
import com.example.backend.model.Problem;

import java.util.List;

public interface CommentService {

    Comment postComment(UserDto userInfo, Problem problem, String content);

    void deleteComment(String commentId);

    List<Comment> getAllCommentsForProblem(String problemId, OrderBy orderBy);

    List<Comment> getAllCommentsForUser(long userId, OrderBy orderBy);

}
