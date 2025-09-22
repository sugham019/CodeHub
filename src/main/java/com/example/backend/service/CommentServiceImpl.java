package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Comment;
import com.example.backend.model.OrderBy;
import com.example.backend.model.Problem;
import com.example.backend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment postComment(UserDto userInfo, Problem problem, String content) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Comment comment = new Comment(userInfo.getId(), userInfo.getDisplayName(), problem.getId(), content, currentDateTime);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getAllCommentsForProblem(String problemId, OrderBy orderBy) {
        if(orderBy == OrderBy.NEWEST){
            return commentRepository.findByProblemIdOrderByPostedAtDesc(problemId);
        }
        if(orderBy == OrderBy.OLDEST){
            return commentRepository.findByProblemIdOrderByPostedAtAsc(problemId);
        }
        return List.of();
    }

    @Override
    public List<Comment> getAllCommentsForUser(long userId, OrderBy orderBy) {
        if(orderBy == OrderBy.NEWEST){
            return commentRepository.findByUserIdOrderByPostedAtAsc(userId);
        }
        if(orderBy == OrderBy.OLDEST){
            return commentRepository.findByUserIdOrderByPostedAtDesc(userId);
        }
        return List.of();
    }

}
