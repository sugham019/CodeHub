package com.example.backend.service;

import com.example.backend.dto.ProblemDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Problem;
import com.example.backend.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemServiceImpl implements ProblemService{

    private final ProblemRepository problemRepository;


    public ProblemServiceImpl(ProblemRepository problemRepository){
        this.problemRepository = problemRepository;
    }

    @Override
    public String addProblem(ProblemDto problemDto) {
        String[] inputs = problemDto.getInputs();
        String[] expectedOutputs = problemDto.getExpectedOutputs();

        if(inputs.length != expectedOutputs.length){
            throw new RuntimeException("Test case inputs and expected outputs size is not equal");
        }
        LocalDate currentDate = LocalDate.now();
        Problem problem = new Problem(problemDto.getTitle(), problemDto.getDifficulty(), problemDto.getPageDescription(), problemDto.getHint(),
                problemDto.getInputs(), problemDto.getInputType(), problemDto.getExpectedOutputs(), problemDto.getOutputType(), currentDate,
                problemDto.getBannedLibrary());
        return problemRepository.save(problem).getId();
    }

    @Override
    public Problem getProblemById(String problemId) {
        Optional<Problem> problemOptional = problemRepository.findById(problemId);
        if(problemOptional.isEmpty()){
            throw new ResourceNotFoundException("Problem does not exists with given id: "+problemId);
        }
        return problemOptional.get();
    }

    @Override
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public void removeProblemById(String problemId) {
        if(!problemRepository.existsById(problemId)){
            throw new ResourceNotFoundException("Problem does not exists with given id: "+problemId);
        }
        problemRepository.deleteById(problemId);
    }



}
