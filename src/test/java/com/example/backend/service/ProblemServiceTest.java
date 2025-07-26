package com.example.backend.service;

import com.example.backend.dto.ProblemDto;
import com.example.backend.model.DataType;
import com.example.backend.model.Difficulty;
import com.example.backend.model.Problem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProblemServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProblemServiceTest.class);

    @Autowired
    private ProblemService problemService;
    private String problemId;

    @BeforeEach
    public void addSampleProblem(){
        String pageDescription = """
            <h1> Reverse a string </h1>
            """;
        String[] inputs = {"sugham", "kharel"};
        String [] expectedOutputs = {"mahgus", "lerahk"};
        ProblemDto problemDto = new ProblemDto("Reverse a string", pageDescription, inputs, DataType.STRING, expectedOutputs, DataType.STRING, Difficulty.EASY);
        problemId = problemService.addProblem(problemDto);
        log.info("Added problem with id: {}", problemId);
    }

    @Test
    public void testSearchProblem(){
        Problem problem = problemService.getProblemInformation(problemId);
        log.info("Found problem with id{}:\n {}", problemId, problem.toString());
    }

    @AfterEach
    public void removeSampleProblem(){
        problemService.removeProblem(problemId);
        log.info("Removed problem with id: {}", problemId);
    }
}
