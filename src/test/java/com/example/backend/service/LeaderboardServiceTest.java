package com.example.backend.service;

import com.example.backend.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LeaderboardServiceTest {

    private static final Logger log = LoggerFactory.getLogger(LeaderboardServiceTest.class);

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void setupUser(){
        user = userService.getUser("test123");
    }

    @Test
    public void increaseRating(){
        leaderboardService.updateRating(user, 20);
        log.info("Updated rating for {} is {}", user.getDisplayName(), user.getLeaderboard().getRating());
    }

    @AfterEach
    public void reset(){
        leaderboardService.updateRating(user, -20);
        log.info("Reset rating for {} to {}", user.getDisplayName(), user.getLeaderboard().getRating());
    }
}
