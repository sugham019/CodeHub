package com.example.backend.repository;

import com.example.backend.model.Problem;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, String> {

    @Aggregation(pipeline = { "{ '$sample': { 'size': 1 } }" })
    Problem getRandomProblem();

}
