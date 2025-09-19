package com.example.backend.repository;

import com.example.backend.model.Difficulty;
import com.example.backend.model.Problem;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.regex.Pattern;

public interface ProblemRepository extends MongoRepository<Problem, String> {

    @Aggregation(pipeline = { "{ '$sample': { 'size': 1 } }" })
    Problem getRandom();

    long countByDifficulty(Difficulty difficulty);

    List<Problem> findByTitleRegex(Pattern regex);

}
