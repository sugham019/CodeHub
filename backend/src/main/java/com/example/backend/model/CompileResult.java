package com.example.backend.model;

public class CompileResult {

    private final boolean success;
    private String errorLogs;
    private final boolean analysisAvailable;
    private float executionSpeedInMilliSecond;
    private float totalMemoryConsumedInMB;

    public CompileResult(){
        this.success = true;
        this.analysisAvailable = false;
    }

    public CompileResult(float executionSpeedInMilliSecond, float totalMemoryConsumedInMB){
        this.success = true;
        this.executionSpeedInMilliSecond = executionSpeedInMilliSecond;
        this.totalMemoryConsumedInMB = totalMemoryConsumedInMB;
        this.analysisAvailable = true;
    }

    public CompileResult(String errorLogs){
        this.success = false;
        this.errorLogs = errorLogs;
        this.analysisAvailable = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorLogs(){
        return errorLogs;
    }

    public boolean isAnalysisAvailable(){
        return analysisAvailable;
    }

    public float getExecutionSpeedInMilliSecond(){
        return executionSpeedInMilliSecond;
    }

    public float getTotalMemoryConsumedInMB(){
        return totalMemoryConsumedInMB;
    }

}
