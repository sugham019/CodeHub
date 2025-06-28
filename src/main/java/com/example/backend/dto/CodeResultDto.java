package com.example.backend.dto;

public class CodeResultDto {

    private boolean allTestPassed;
    private String logs;
    private double executionTimeInMs;

    public CodeResultDto(boolean allTestPassed, String logs, double executionTimeInMs){
        this.allTestPassed = allTestPassed;
        this.logs = logs;
        this.executionTimeInMs = executionTimeInMs;
    }

    public boolean isAllTestPassed(){
        return allTestPassed;
    }

    public String getLogs(){
        return logs;
    }

    public double getExecutionTimeInMs(){
        return executionTimeInMs;
    }

}
