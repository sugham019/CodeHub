package com.example.backend.service;

import java.util.Map;

public interface LeaderboardService {

    Map<String, Integer> getTopRankers(int limit);

}
