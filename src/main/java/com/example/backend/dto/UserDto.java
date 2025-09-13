package com.example.backend.dto;

public class UserDto {

    private String email;
    private String displayName;
    private String recentProblemId;
    private int ratings;
    private long id;

    public UserDto(long id, String email, String displayName, int ratings, String recentProblemId){
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.ratings = ratings;
        this.recentProblemId = recentProblemId;
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

    public String getDisplayName(){
        return displayName;
    }

    public int getRatings(){
        return ratings;
    }

}
