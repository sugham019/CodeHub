package com.example.backend.service;

import com.example.backend.dto.CodeSubmitDto;
import com.example.backend.dto.CodeResultDto;

public abstract class CodeService {

    public abstract CodeResultDto compileAndRun(CodeSubmitDto codeInput);

    public void validateSubmittedCode(CodeSubmitDto codeSubmitDto){
        if(codeSubmitDto.getInputs().length != codeSubmitDto.getExpectedOutputs().length){
            throw new RuntimeException("Test Case Error: Inputs length and Expected Output length is not equal");
        }
        // Todo
    }

}
