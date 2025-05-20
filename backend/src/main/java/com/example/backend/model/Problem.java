package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    private String id;

    private String title;
    private String pagePath;

    @ManyToMany
    private Set<User> users;

    public Problem(String id, String title, String pagePath){
        this.id = id;
        this.title = title;
        this.pagePath = pagePath;
    }

    public Problem() {

    }

    public String getPagePath(){
        return pagePath;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
}
