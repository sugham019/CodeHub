package com.example.backend.dto;

import com.example.backend.model.DataType;
import com.example.backend.model.Difficulty;
import com.example.backend.model.Import;

public class ProblemDto {

    private String title;
    private String description;
    private String[] inputs;
    private DataType inputType;
    private String[] expectedOutputs;
    private DataType outputType;

    private Difficulty difficulty;
    private Import bannedImport;
    private String hint;

    public ProblemDto(){

    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String[] getInputs(){
        return inputs;
    }

    public DataType getInputType() {
        return inputType;
    }

    public DataType getOutputType() {
        return outputType;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public Import getBannedImport(){
        return bannedImport;
    }

    public String getHint(){
        return hint;
    }

    public String[] getExpectedOutputs(){
        return expectedOutputs;
    }



}
