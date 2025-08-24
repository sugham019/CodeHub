package com.example.backend.service;

import com.example.backend.model.User;

import java.util.Map;

public interface LeaderboardService {

    Map<String, Integer> getTopRankers(int limit);
    void updateRating(User user, int offset);

}
