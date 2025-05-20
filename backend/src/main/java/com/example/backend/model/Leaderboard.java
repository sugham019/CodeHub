package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "leaderboard")
    private User user;

    private int rating;

    public Leaderboard(User user, int rating){
        this.user = user;
        this.rating = rating;
    }

    public Leaderboard(){

    }
}
