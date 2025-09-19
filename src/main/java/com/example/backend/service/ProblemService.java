package com.example.backend.service;

import com.example.backend.dto.ProblemCountDto;
import com.example.backend.dto.ProblemDto;
import com.example.backend.model.Problem;

import java.util.List;

public interface ProblemService {

    String addProblem(ProblemDto problemDto);

    Problem getProblemById(String problemId);

    List<Problem> searchProblemsByTitle(String searchTerm);

    List<Problem> getRecentSolvedProblems(long id);

    ProblemCountDto getTotalProblems();

    Problem getRandomProblem();

    boolean problemExists(String problemId);

    String[] getSkills(String problemId);

    List<Problem> getAllProblems();

    void deleteProblemById(String problemId);

}
