package com.example.backend.repository;

import com.example.backend.model.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, String>{

    @Query("SELECT COUNT(l) + 1 FROM Leaderboard l WHERE l.rating > :rating")
    int getRank(@Param("rating") int rating);

}
