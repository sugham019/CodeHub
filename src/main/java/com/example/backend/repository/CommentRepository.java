package com.example.backend.repository;

import com.example.backend.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByProblemIdOrderByPostedAtAsc(String problemId);

    List<Comment> findByProblemIdOrderByPostedAtDesc(String problemId);

    List<Comment> findByUserIdOrderByPostedAtAsc(long userId);

    List<Comment> findByUserIdOrderByPostedAtDesc(long userId);

}
