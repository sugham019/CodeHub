package com.example.backend.repository;

import com.example.backend.model.SolvedProblem;
import com.example.backend.model.SolvedProblemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, SolvedProblemId> {

}
