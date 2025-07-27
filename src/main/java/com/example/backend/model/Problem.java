package com.example.backend.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Arrays;

@Document(collection = "problems")
public class Problem {

    @Id
    private String id;

    private String title;
    private String pageDescription;

    private DataType inputType;
    private DataType outputType;

    private String[] inputs;
    private String[] expectedOutputs;
    private Difficulty difficulty;
    private LocalDate datePosted;
    private Import bannedImport;
    private String hint;

    public Problem(){

    }

    public Problem(String title, Difficulty difficulty, String pageDescription, String hint, String[] inputs, DataType inputType, String[] expectedOutputs, DataType outputType, LocalDate datePosted, Import bannedImport){
        this.title = title;
        this.difficulty = difficulty;
        this.pageDescription = pageDescription;
        this.inputs = inputs;
        this.inputType = inputType;
        this.expectedOutputs = expectedOutputs;
        this.outputType = outputType;
        this.hint = hint;
        this.datePosted = datePosted;
        this.bannedImport = bannedImport;
    }

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPageDescription(){
        return pageDescription;
    }

    public DataType getInputType() {
        return inputType;
    }

    public DataType getOutputType() {
        return outputType;
    }

    public String[] getInputs() {
        return inputs;
    }

    public String[] getExpectedOutputs() {
        return expectedOutputs;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public Import getBannedImport() {
        return bannedImport;
    }

    public String getHint() {
        return hint;
    }

}
