package com.example.backend.dto;

public class CodeResultDto {

    private boolean allTestPassed;
    private String logs;
    private double executionTimeInMs;
    private double peakMemoryUsageKB;

    public CodeResultDto(boolean allTestPassed, String logs, double executionTimeInMs, double peakMemoryUsageKB){
        this.allTestPassed = allTestPassed;
        this.logs = logs;
        this.executionTimeInMs = executionTimeInMs;
        this.peakMemoryUsageKB = peakMemoryUsageKB;
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

    public double getPeakMemoryUsageKB() {
        return peakMemoryUsageKB;
    }
}