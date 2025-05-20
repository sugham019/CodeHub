package com.example.backend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String displayName;
    private String passwordHash;

    @ManyToMany
    @JoinTable(name = "user_problems",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "problem_id"))
    private Set<Problem> solvedProblems;

    @OneToOne
    @JoinColumn(name = "leaderboard_id")
    private Leaderboard leaderboard;

    public User(String username, String displayName){
        this.username = username;
        this.displayName = displayName;
        this.solvedProblems = new HashSet<>();
    }

    public User(){

    }

    public Set<Problem> getSolvedProblems(){
        return solvedProblems;
    }

    public String getUsername(){
        return username;
    }

    public String getDisplayName(){
        return displayName;
    }
}
