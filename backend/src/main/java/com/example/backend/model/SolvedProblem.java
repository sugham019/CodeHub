package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "solved_problem")
public class SolvedProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String problemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate solvedDate;

    public SolvedProblem(){

    }

    public SolvedProblem(String problemId, User user, LocalDate solvedDate){
        this.problemId = problemId;
        this.user = user;
        this.solvedDate = solvedDate;
    }

}
