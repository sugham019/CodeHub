package com.example.backend.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problems")
public class Problem {

    @Id
    private String id;

    //  Todo: add necessary fields

}
