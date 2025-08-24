package com.example.backend.dto;

import com.example.backend.model.DataType;
import com.example.backend.model.Difficulty;
import com.example.backend.model.Language;

import java.util.Map;

public class ProblemDto {

    private String title;
    private String pageDescription
;
    private String[] inputs;
    private DataType inputType;
    private String[] expectedOutputs;
    private DataType outputType;

    private Difficulty difficulty;
    private Map<Language, String> bannedLibrary;
    private String hint;

    public ProblemDto(String title, String pageDescription, String[] inputs, DataType inputType, String[] expectedOutputs, DataType outputType,
                      Difficulty difficulty, Map<Language, String> bannedLibrary, String hint){
        this.title = title;
        this.pageDescription = pageDescription;
        this.inputs = inputs;
        this.bannedLibrary = bannedLibrary;
        this.inputType = inputType;
        this.expectedOutputs = expectedOutputs;
        this.outputType = outputType;
        this.hint = hint;
        this.difficulty = difficulty;
    }

    public ProblemDto(){

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

    public Map<Language, String> getBannedLibrary(){
        return bannedLibrary;
    }

    public String getHint(){
        return hint;
    }

    public String[] getExpectedOutputs(){
        return expectedOutputs;
    }

}
