package com.example.backend.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SolvedProblemId implements Serializable {

    private String problemId;
    private String username;

    public SolvedProblemId() {

    }

    public SolvedProblemId(String problemId, String username) {
        this.problemId = problemId;
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SolvedProblemId otherId)) return false;

        return Objects.equals(problemId, otherId.problemId) && Objects.equals(username, otherId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, username);
    }

    public String getProblemId() {
        return problemId;
    }

    public String getUsername() {
        return username;
    }

}