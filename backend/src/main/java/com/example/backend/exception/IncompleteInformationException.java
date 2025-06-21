package com.example.backend.exception;

public class IncompleteInformationException  extends RuntimeException{

    public IncompleteInformationException(){
        super("Incomplete Information Submitted");
    }

    public IncompleteInformationException(String message){
        super(message);
    }

}
