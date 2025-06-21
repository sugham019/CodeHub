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

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @ManyToMany
    @JoinTable(name = "users_problems",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "problem_id"))
    private Set<Problem> solvedProblems;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Leaderboard leaderboard;

    public User(String username, String displayName, String passwordHash, LocalDate birthDate, AccessLevel accessLevel){
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
        this.solvedProblems = new HashSet<>();
        this.accessLevel = accessLevel;
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

    public void setAccessLevel(AccessLevel accessLevel){
        this.accessLevel = accessLevel;
    }

    public AccessLevel getAccessLevel(){
        return accessLevel;
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
