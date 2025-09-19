package com.example.backend.service;

import com.example.backend.dto.ProblemCountDto;
import com.example.backend.dto.ProblemDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.*;
import com.example.backend.repository.ProblemRepository;
import com.example.backend.repository.SolvedProblemRepository;
import com.example.backend.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ProblemServiceImpl implements ProblemService{

    private static final Logger log = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private final ProblemRepository problemRepository;

    private final TagRepository tagRepository;

    private final SolvedProblemRepository solvedProblemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository, SolvedProblemRepository solvedProblemRepository,
                              TagRepository tagRepository){
        this.problemRepository = problemRepository;
        this.solvedProblemRepository = solvedProblemRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public String addProblem(ProblemDto problemDto) {
        String[] inputs = problemDto.getInputs();
        String[] expectedOutputs = problemDto.getExpectedOutputs();

        if(inputs.length != expectedOutputs.length){
            throw new RuntimeException("Test case inputs and expected outputs size is not equal");
        }
        LocalDate currentDate = LocalDate.now();
        Problem problem = new Problem(problemDto.getTitle(), problemDto.getDifficulty(), problemDto.getPageDescription(), problemDto.getInputs(), problemDto.getInputType(),
                problemDto.getExpectedOutputs(), problemDto.getOutputType(), currentDate, problemDto.getBannedLibrary(),
                problemDto.getSkills(), problemDto.getHint());

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
    public List<Problem> searchProblemsByTitle(String searchTerm) {
        Pattern pattern = Pattern.compile(Pattern.quote(searchTerm), Pattern.CASE_INSENSITIVE);
        return problemRepository.findByTitleRegex(pattern);
    }

    @Override
    public List<Problem> getRecentSolvedProblems(long userId) {
        LocalDate timeframe = LocalDate.now().minusMonths(1);
        List<SolvedProblem> solvedProblems = solvedProblemRepository.findRecentProblemsByUser(userId, timeframe);

        List<Problem> problems = new ArrayList<>(solvedProblems.size());
        for (SolvedProblem solvedProblem : solvedProblems) {
            problems.add(getProblemById(solvedProblem.getId().getProblemId()));
        }
        return problems;
    }

    @Override
    public ProblemCountDto getTotalProblems() {
        long easyCount = problemRepository.countByDifficulty(Difficulty.EASY);
        long mediumCount = problemRepository.countByDifficulty(Difficulty.MEDIUM);
        long hardCount = problemRepository.countByDifficulty(Difficulty.HARD);
        return new ProblemCountDto(easyCount, mediumCount, hardCount);
    }

    @Override
    public Problem getRandomProblem() {
        return problemRepository.getRandom();
    }

    @Override
    public boolean problemExists(String problemId) {
        return problemRepository.existsById(problemId);
    }

    @Override
    public String[] getSkills(String problemId) {
        Problem problem = getProblemById(problemId);
        return problem.getSkills();
    }

    @Override
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public void deleteProblemById(String problemId) {
        if(!problemRepository.existsById(problemId)){
            throw new ResourceNotFoundException("Problem does not exists with given id: "+problemId);
        }
        problemRepository.deleteById(problemId);
        solvedProblemRepository.deleteByProblemId(problemId);
    }

}
