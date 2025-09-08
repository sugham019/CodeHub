package com.example.backend.controller.api;

import com.example.backend.dto.UserDto;
import com.example.backend.service.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService){
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/getTopRankers")
    public ResponseEntity<List<UserDto>> getTopRankers(@RequestParam(defaultValue = "10") int limit) {
        return new ResponseEntity<>(leaderboardService.getTopRankers(limit), HttpStatus.OK);
    }

}
