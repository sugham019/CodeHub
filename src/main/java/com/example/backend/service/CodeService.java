package com.example.backend.service;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.exception.CodeSubmissionException;
import com.example.backend.model.*;

public abstract class CodeService {

    private final UserService userService;

    private final Language language;

    public CodeService(Language language, UserService userService){
        this.language = language;
        this.userService = userService;
    }

    public abstract CodeResultDto compileAndRun(String code, DataType inputType, String[] inputs, DataType outputType, String[] expectedOutputs);

    protected abstract boolean isUsingLibrary(String code, String library);

    public CodeResultDto submit(String email, Problem problem, String code){
        String bannedLibrary = problem.getBannedLibrary().get(language);
        if(bannedLibrary != null && isUsingLibrary(code, bannedLibrary)){
            throw new CodeSubmissionException("Cannot use library: " + bannedLibrary);
        }
        CodeResultDto result = compileAndRun(code, problem.getInputType(), problem.getInputs(), problem.getOutputType(), problem.getExpectedOutputs());
        if(result.isAllTestPassed()){
            userService.solveProblem(email, problem);
            userService.addTag(email, TagType.LANGUAGE, this.language.toString()); // addTag() will auto filter if duplicate

            for(String skill: problem.getSkills()){
                userService.addTag(email, TagType.SKILL, skill);
            }
        }
        return result;
    }

}