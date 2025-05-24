package com.example.backend.dto;

import java.time.LocalDate;

public class CreateUserDTO {

    private String username;
    private String displayName;
    private String password;
    private LocalDate birthDate;

    public CreateUserDTO(){

    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getDisplayName(){
        return displayName;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

}
