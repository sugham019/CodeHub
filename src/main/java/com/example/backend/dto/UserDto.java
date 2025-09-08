package com.example.backend.dto;

public class UserDto {

    private String email;
    private String displayName;
    private int ratings;
    private long id;

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
