package com.example.backend.dto;

import java.time.LocalDate;

public class UserDto {

    private String email;
    private String displayName;
    private String recentProblemId;
    private int ratings;
    private LocalDate dob;
    private long id;

    public UserDto(long id, String email, String displayName, int ratings, String recentProblemId, LocalDate dob){
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.ratings = ratings;
        this.recentProblemId = recentProblemId;
        this.dob = dob;
    }

    public UserDto(long id, String email, String displayName, int ratings){
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.ratings = ratings;
    }

    public UserDto(){

    }

    public String getEmail(){
        return email;
    }

    public String getRecentProblemId() {
        return recentProblemId;
    }

    public long getId(){
        return id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getDisplayName(){
        return displayName;
    }

    public int getRatings(){
        return ratings;
    }

}
