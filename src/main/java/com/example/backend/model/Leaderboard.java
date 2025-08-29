package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @Column(name = "email")
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
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

    public void setRating(int rating){
        this.rating = rating;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

}
