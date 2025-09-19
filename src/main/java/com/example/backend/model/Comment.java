package com.example.backend.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Indexed
    private String problemId;

    @Indexed
    private long userId;

    private String userDisplayName;
    private String content;
    private LocalDateTime postedAt;

    public Comment() {

    }

    public Comment(long userId, String userDisplayName, String problemId, String content, LocalDateTime postedAt) {
        this.problemId = problemId;
        this.userId = userId;
        this.content = content;
        this.postedAt = postedAt;
        this.userDisplayName = userDisplayName;
    }

    public String getId() {
        return id;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }
}
