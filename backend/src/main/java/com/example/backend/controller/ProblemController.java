package com.example.backend.controller;

import com.example.backend.dto.ProblemDto;
import com.example.backend.model.Problem;
import com.example.backend.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService){
        this.problemService = problemService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProblem(@RequestBody ProblemDto problemDto){
        String problemId = problemService.addProblem(problemDto);
        return new ResponseEntity<>(problemId, HttpStatus.CREATED);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestParam String problemId){
        problemService.removeProblem(problemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getDetails")
    public ResponseEntity<Problem> getDetails(@RequestParam String problemId){
        Problem problem = problemService.getProblemInformation(problemId);
        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

}
