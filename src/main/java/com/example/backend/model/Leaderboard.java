package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
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
