package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;

import java.util.List;

public interface LeaderboardService {

    List<UserDto> getTopRankers(int limit);

    void updateRating(User user, int offset);

    int getUserRankByRating(int rating);

}
