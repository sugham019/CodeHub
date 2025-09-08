package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String displayName;
    private String passwordHash;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email")
    private List<SolvedProblem> solvedProblems;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Leaderboard leaderboard;

    public User(String email, String displayName, String passwordHash, LocalDate birthDate, AccessLevel accessLevel){
        this.email = email;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
        this.solvedProblems = new ArrayList<>();
        this.accessLevel = accessLevel;
    }

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public long getId(){
        return id;
    }

    public List<SolvedProblem> getSolvedProblems(){
        return solvedProblems;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
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
