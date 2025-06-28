package com.example.backend.service;

import com.example.backend.dto.ProblemDto;
import com.example.backend.model.Problem;

import java.util.List;

public interface ProblemService {

    String addProblem(ProblemDto problemDto);
    Problem getProblemInformation(String problemId);
    List<Problem> findByTitle(String title);
    void removeProblem(String problemId);

}
