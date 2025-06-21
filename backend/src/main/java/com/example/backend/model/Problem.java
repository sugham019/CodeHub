package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    private String id;
    private String title;

    public Problem(String id, String title, String pagePath){
        this.id = id;
        this.title = title;
    }

    public Problem() {

    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

}
