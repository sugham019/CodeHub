package com.example.backend.exception;

public class IncompleteInformationException  extends RuntimeException{

    public IncompleteInformationException(){
        super("Request body is incomplete");
    }

}
