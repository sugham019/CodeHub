package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<SolvedProblem> solvedProblems;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Leaderboard leaderboard;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    private String recentProblemId;

    private String profilePicturePath;
    private String profilePictureType;

    private int totalProfileViews;

    public User(String email, String displayName, String passwordHash, LocalDate birthDate, AccessLevel accessLevel){
        this.email = email;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
        this.solvedProblems = new HashSet<>();
        this.tags = new HashSet<>();
        this.accessLevel = accessLevel;
        this.totalProfileViews = 0;
    }

    public User(){

    }

    public int getTotalProfileViews() {
        return totalProfileViews;
    }

    public void setTotalProfileViews(int totalProfileViews) {
        this.totalProfileViews = totalProfileViews;
    }

    public String getEmail() {
        return email;
    }

    public long getId(){
        return id;
    }

    public Set<SolvedProblem> getSolvedProblems(){
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

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getProfilePictureType() {
        return profilePictureType;
    }

    public void setProfilePictureType(String profilePictureType) {
        this.profilePictureType = profilePictureType;
    }

    public String getRecentProblemId() {
        return recentProblemId;
    }

    public void setRecentProblemId(String recentProblemId) {
        this.recentProblemId = recentProblemId;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
