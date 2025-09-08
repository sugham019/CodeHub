package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "solved_problem")
public class SolvedProblem {

    @EmbeddedId
    private SolvedProblemId id;

    private LocalDate solvedDate;

    private String title;

    public SolvedProblem(){

    }

    public SolvedProblem(String problemId, Long userId, String title, LocalDate solvedDate){
        this.id = new SolvedProblemId(problemId, userId);
        this.title = title;
        this.solvedDate = solvedDate;
    }

    public LocalDate getSolvedDate(){
        return solvedDate;
    }

    public String getTitle(){
        return title;
    }

    public SolvedProblemId getId(){
        return id;
    }

}
