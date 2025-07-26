package com.example.backend.dto;

import com.example.backend.model.DataType;
import com.example.backend.model.Difficulty;
import com.example.backend.model.Import;

public class ProblemDto {

    private String title;
    private String pageDescription
;
    private String[] inputs;
    private DataType inputType;
    private String[] expectedOutputs;
    private DataType outputType;

    private Difficulty difficulty;
    private Import bannedImport;
    private String hint;

    public ProblemDto(String title, String pageDescription, String[] inputs, DataType inputType, String[] expectedOutputs, DataType outputType,
                      Difficulty difficulty){
        this.title = title;
        this.pageDescription = pageDescription;
        this.inputs = inputs;
        this.inputType = inputType;
        this.expectedOutputs = expectedOutputs;
        this.outputType = outputType;
        this.difficulty = difficulty;
    }

    public ProblemDto(){

    }

    public void setBannedImport(Import bannedImport){
        this.bannedImport = bannedImport;
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    public String getTitle(){
        return title;
    }

    public String getPageDescription(){
        return pageDescription;
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
