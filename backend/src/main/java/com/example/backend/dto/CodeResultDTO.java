package com.example.backend.dto;

public class CodeResultDTO {

    private final boolean allTestPassed;
    private final String logs;
    private final double executionTimeInMs;

    public CodeResultDTO(boolean allTestPassed, String logs, double executionTimeInMs){
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
