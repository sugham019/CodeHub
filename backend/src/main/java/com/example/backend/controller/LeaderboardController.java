package com.example.backend.controller;

import com.example.backend.service.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService){
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/getTopRankers")
    public ResponseEntity<Map<String, Integer>> getTopRankers(@RequestParam(defaultValue = "10") int limit) {
        return new ResponseEntity<>(leaderboardService.getTopRankers(limit), HttpStatus.OK);
    }

}
