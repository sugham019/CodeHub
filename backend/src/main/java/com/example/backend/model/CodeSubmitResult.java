package com.example.backend.model;

public class CodeSubmitResult {

    private final boolean allTestPassed;
    private final String logs;
    private final double executionTimeInMs;

    public CodeSubmitResult(boolean allTestPassed, String logs, double executionTimeInMs){
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
