//package com.example.backend.service;
//
//import com.example.backend.dto.ProblemDto;
//import com.example.backend.model.DataType;
//import com.example.backend.model.Difficulty;
//import com.example.backend.model.Language;
//import com.example.backend.model.Problem;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Map;
//
//@SpringBootTest
//public class ProblemServiceTest {
//
//    private static final Logger log = LoggerFactory.getLogger(ProblemServiceTest.class);
//
//    @Autowired
//    private ProblemService problemService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private String problemId;
//
//    @BeforeEach
//    public void addSampleProblem(){
//        String pageDescription = """
//            <h1> Reverse a string </h1>
//            """;
//        String[] inputs = {"sugham", "kharel"};
//        String [] expectedOutputs = {"mahgus", "lerahk"};
//        Map<Language, String> bannedLibraries = Map.of(
//                Language.JAVA, "java.util"
//        );
//        ProblemDto problemDto = new ProblemDto("Reverse a string", pageDescription, inputs, DataType.STRING, expectedOutputs, DataType.STRING, Difficulty.EASY, bannedLibraries, null);
//        problemId = problemService.addProblem(problemDto);
//        log.info("Added problem with id: {}", problemId);
//    }
//
//    @Test
//    public void testSearchProblem() throws JsonProcessingException {
//        Problem problem = problemService.getProblemById(problemId);
//        String problemJson;
//        try {
//            problemJson = objectMapper.writeValueAsString(problem);
//        } catch (JsonProcessingException e) {
//            log.error("Failed to convert Problem Object (id : {}) to JSON", problemId);
//            throw e;
//        }
//        log.info("Found problem with id{}:\n {}", problemId, problemJson);
//    }
//
//    @AfterEach
//    public void removeSampleProblem(){
//        problemService.deleteProblemById(problemId);
//        log.info("Removed problem with id: {}", problemId);
//    }
//}
