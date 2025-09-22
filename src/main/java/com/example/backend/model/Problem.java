package com.example.backend.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Map<Language, String> bannedLibrary;
    private String[] skills;
    private String hint;

    public Problem(){

    }

    public Problem(String title, Difficulty difficulty, String pageDescription, String[] inputs, DataType inputType, String[] expectedOutputs, DataType outputType,
                   LocalDate datePosted, Map<Language, String> bannedLibrary, String[] skills, String hint){
        this.title = title;
        this.difficulty = difficulty;
        this.pageDescription = pageDescription;
        this.inputs = inputs;
        this.inputType = inputType;
        this.expectedOutputs = expectedOutputs;
        this.outputType = outputType;
        this.hint = hint;
        this.datePosted = datePosted;
        this.bannedLibrary = bannedLibrary;
        this.skills = skills;
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

    public int getPoints() {
        return this.difficulty != null ? this.difficulty.getValue() : 0;
    }

    public Map<Language, String> getBannedLibrary() {
        return bannedLibrary;
    }

    public String getHint() {
        return hint;
    }

    public String[] getSkills() {
        return skills;
    }
}
