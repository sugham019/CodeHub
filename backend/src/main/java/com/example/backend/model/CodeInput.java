package com.example.backend.model;

public class CodeInput {

    private String code;
    private DataType inputType;
    private DataType outputType;
    private int totalTests;
    private String[] inputs;
    private String[] expectedOutputs;

    public CodeInput(){

    }

    public String getCode() {
        return code;
    }

    public DataType getInputType() {
        return inputType;
    }

    public DataType getOutputType() {
        return outputType;
    }

    public String[] getInputs(){
        return inputs;
    }

    public String[] getExpectedOutputs() {
        return expectedOutputs;
    }

    public int getTotalTests(){
        return totalTests;
    }
}
