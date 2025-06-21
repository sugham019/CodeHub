package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @Column(name = "username")
    private String username;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private User user;

    private int rating;

    public Leaderboard(int rating){
        this.rating = rating;
    }

    public Leaderboard(){

    }

    public int getRating(){
        return rating;
    }

    public void updateRating(int offset){
        rating = rating + offset;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

}
