package com.example.backend.service;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.exception.CodeSubmissionException;
import com.example.backend.model.*;
import com.example.backend.utiil.AuthUtil;

public abstract class CodeService {

    private final UserService userService;

    private final ProblemService problemService;

    private final Language language;

    public CodeService(Language language, UserService userService, ProblemService problemService){
        this.language = language;
        this.userService = userService;
        this.problemService = problemService;
    }

    public abstract CodeResultDto compileAndRun(String code, DataType inputType, String[] inputs, DataType outputType, String[] expectedOutputs);

    protected abstract boolean isUsingLibrary(String code, String library);

    public CodeResultDto submit(String problemId, String code){
        Problem problem = problemService.getProblemById(problemId);
        String bannedLibrary = problem.getBannedLibrary().get(language);
        if(bannedLibrary != null && isUsingLibrary(code, bannedLibrary)){
            throw new CodeSubmissionException("Cannot use library: "+ bannedLibrary);
        }
        CodeResultDto result = compileAndRun(code, problem.getInputType(), problem.getInputs(), problem.getOutputType(), problem.getExpectedOutputs());
        if(result.isAllTestPassed()){
            User user = userService.getUser(AuthUtil.getUsername());
            userService.solveProblem(user, problem);
        }
        return result;
    }

}
