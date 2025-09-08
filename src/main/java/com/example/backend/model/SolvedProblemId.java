package com.example.backend.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SolvedProblemId implements Serializable {

    private String problemId;
    private Long userId;

    public SolvedProblemId() {

    }

    public SolvedProblemId(String problemId, Long userId) {
        this.problemId = problemId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SolvedProblemId otherId)) return false;

        return Objects.equals(problemId, otherId.problemId) && Objects.equals(userId, otherId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, userId);
    }

    public String getProblemId() {
        return problemId;
    }

    public long getUserId(){
        return userId;
    }
}