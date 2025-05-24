package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String displayName;
    private String passwordHash;
    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(name = "users_problems",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "problem_id"))
    private Set<Problem> solvedProblems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leaderboard_id")
    private Leaderboard leaderboard;

    public User(String username, String displayName, String passwordHash, LocalDate birthDate){
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
        this.solvedProblems = new HashSet<>();
    }

    public User(){

    }

    public Set<Problem> getSolvedProblems(){
        return solvedProblems;
    }

    public void addSolvedProblem(Problem problem){
        solvedProblems.add(problem);
    }

    public String getUsername(){
        return username;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void updatePasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setLeaderboard(Leaderboard leaderboard){
        this.leaderboard = leaderboard;
    }

    public Leaderboard getLeaderboard(){
        return leaderboard;
    }

    public void updateDisplayName(){
        this.displayName = displayName;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

}
