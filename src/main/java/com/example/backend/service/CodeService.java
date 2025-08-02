package com.example.backend.service;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.exception.CodeSubmissionException;
import com.example.backend.model.DataType;
import com.example.backend.model.Difficulty;
import com.example.backend.model.Problem;
import com.example.backend.model.User;
import com.example.backend.utiil.AuthUtil;

public abstract class CodeService {

    private final UserService userService;

    private final ProblemService problemService;

    private final LeaderboardService leaderboardService;

    public CodeService(UserService userService, LeaderboardService leaderboardService, ProblemService problemService){
        this.userService = userService;
        this.leaderboardService = leaderboardService;
        this.problemService = problemService;
    }

    public abstract CodeResultDto compileAndRun(String code, DataType inputType, String[] inputs, DataType outputType, String[] expectedOutputs);

    protected abstract boolean isUsingBannedLibrary();

    protected abstract boolean isCodeSafeToExecute();

    public CodeResultDto submit(String problemId, String code){
        Problem problem = problemService.getProblemInformation(problemId);
        if(isUsingBannedLibrary()){
            throw new CodeSubmissionException("Cannot use library: "+ problem.getBannedImport());
        }
        if(!isCodeSafeToExecute()){
            throw new CodeSubmissionException("Code is not safe to execute");
        }
        Difficulty difficulty = problem.getDifficulty();
        CodeResultDto result = compileAndRun(code, problem.getInputType(), problem.getInputs(), problem.getOutputType(), problem.getExpectedOutputs());

        User user = userService.getUser(AuthUtil.getUsername());
        leaderboardService.updateRating(user, difficulty.getValue());
        return result;
    }

}
