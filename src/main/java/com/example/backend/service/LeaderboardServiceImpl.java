package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Leaderboard;
import com.example.backend.model.User;
import com.example.backend.repository.LeaderboardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardServiceImpl implements LeaderboardService{

    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository){
        this.leaderboardRepository = leaderboardRepository;
    }

    @Override
    public List<UserDto> getTopRankers(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("rating").descending());
        return getLeaderboard(pageable);
    }

    private List<UserDto> getLeaderboard(Pageable pageable) {
        return leaderboardRepository.findAll(pageable)
                .stream()
                .map(lb -> new UserDto(
                        lb.getUser().getId(),
                        lb.getUser().getEmail(),
                        lb.getUser().getDisplayName(),
                        lb.getRating()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateRating(User user, int offset) {
        int currentRating = user.getLeaderboard().getRating();
        int newRating = Math.max(currentRating + offset, 0);
        Leaderboard leaderboard = user.getLeaderboard();
        leaderboard.setRating(newRating);
        leaderboardRepository.save(leaderboard);
    }

    @Override
    public int getUserRankByRating(int rating) {
        return leaderboardRepository.getRank(rating);
    }

}
