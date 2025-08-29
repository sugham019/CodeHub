package com.example.backend.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SolvedProblemId implements Serializable {

    private String problemId;
    private String email;

    public SolvedProblemId() {

    }

    public SolvedProblemId(String problemId, String email) {
        this.problemId = problemId;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SolvedProblemId otherId)) return false;

        return Objects.equals(problemId, otherId.problemId) && Objects.equals(email, otherId.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, email);
    }

    public String getProblemId() {
        return problemId;
    }

    public String getEmail() {
        return email;
    }
}