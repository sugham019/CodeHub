package com.example.backend.repository;

import com.example.backend.model.SolvedProblem;
import com.example.backend.model.SolvedProblemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, SolvedProblemId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SolvedProblem sp WHERE sp.id.problemId = :problemId")
    void deleteByProblemId(String problemId);

    @Query("SELECT sp FROM SolvedProblem sp WHERE sp.id.userId = :userId AND sp.solvedDate >= :fromDate ORDER BY sp.solvedDate DESC")
    List<SolvedProblem> findRecentProblemsByUser(Long userId, LocalDate fromDate);

}
