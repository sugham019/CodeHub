package com.example.backend.service;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.exception.CodeSubmissionException;
import com.example.backend.model.*;
import com.example.backend.utiil.AuthUtil;

import java.util.concurrent.*;

public abstract class CodeService {

    private final UserService userService;

    private final ProblemService problemService;

    private final Language language;

    private final ExecutorService codeExecutor = Executors.newSingleThreadExecutor();

    private static final int CODE_TIMEOUT_SEC = 4;

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
        // If the user submitted code runs more than the default CODE_TIMEOUT, terminate it and throw error
        Future<CodeResultDto> future = codeExecutor.submit(() ->
                compileAndRun(code, problem.getInputType(), problem.getInputs(), problem.getOutputType(), problem.getExpectedOutputs())
        );
        CodeResultDto result;
        try {
            result = future.get(CODE_TIMEOUT_SEC, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new CodeSubmissionException("Code took longer than " + CODE_TIMEOUT_SEC + " seconds to execute");
        } catch (Exception e) {
            throw new CodeSubmissionException("Error :" + e.getMessage());
        }
        if(result.isAllTestPassed()){
            User user = userService.getUser(AuthUtil.getUsername());
            userService.solveProblem(user, problem);
        }
        return result;
    }

}
