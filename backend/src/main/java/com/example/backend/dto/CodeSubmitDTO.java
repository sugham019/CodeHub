package com.example.backend.dto;

import com.example.backend.model.DataType;

public class CodeSubmitDTO {

    private String code;
    private DataType inputType;
    private DataType outputType;
    private int totalTests;
    private String[] inputs;
    private String[] expectedOutputs;

    public CodeSubmitDTO(){

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
