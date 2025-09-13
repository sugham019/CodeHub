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

    private String recentProblemId;

    private String profilePictureImageName;
    private String profilePictureImageType;

    @Lob
    private byte[] profilePictureImage;

    public User(String email, String displayName, String passwordHash, LocalDate birthDate, AccessLevel accessLevel){
        this.email = email;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
        this.solvedProblems = new ArrayList<>();
        this.accessLevel = accessLevel;
        this.recentProblemId = null;
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

    public byte[] getProfilePictureImage() {
        return profilePictureImage;
    }

    public void setProfilePictureImage(byte[] profilePictureImage) {
        this.profilePictureImage = profilePictureImage;
    }

    public String getProfilePictureImageType() {
        return profilePictureImageType;
    }

    public void setProfilePictureImageType(String profilePictureImageType) {
        this.profilePictureImageType = profilePictureImageType;
    }

    public String getProfilePictureImageName() {
        return profilePictureImageName;
    }

    public void setProfilePictureImageName(String profilePictureImageName) {
        this.profilePictureImageName = profilePictureImageName;
    }

    public String getRecentProblemId() {
        return recentProblemId;
    }

    public void setRecentProblemId(String recentProblemId) {
        this.recentProblemId = recentProblemId;
    }

}
