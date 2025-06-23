package com.example.backend.service;

import com.example.backend.repository.LeaderboardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaderboardServiceImpl implements LeaderboardService{

    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository){
        this.leaderboardRepository = leaderboardRepository;
    }

    @Override
    public Map<String, Integer> getTopRankers(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("rating").descending());
        return getLeaderboard(pageable);
    }

    private Map<String, Integer> getLeaderboard(Pageable pageable){
        return leaderboardRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toMap(
                        lb -> lb.getUser().getUsername(),
                        lb -> lb.getRating(),
                        (existing, replacement) -> existing,
                        () -> new LinkedHashMap<>()
                ));
    }

}
