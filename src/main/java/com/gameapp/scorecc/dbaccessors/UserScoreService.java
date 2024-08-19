package com.gameapp.scorecc.dbaccessors;

import com.gameapp.scorecc.model.UserScoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserScoreService {

    final int CACHE_REFRESH_TTL = 120000; // In Ms

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Cacheable("top5Scores")
    public List<UserScoreDTO> getTop5ScoresFromDB() {
        List<UserScoreDTO> top5Scores = userScoreRepository.findTop5Scores();
        if(Objects.isNull(top5Scores)){
            return Collections.emptyList();
        }
        return top5Scores;
    }

    public List<UserScoreDTO> findScoresByUserId(Integer userId) {
        List<UserScoreDTO> scoreListForUserId = userScoreRepository.findScoresByUserId(userId);
        if(Objects.isNull(scoreListForUserId)){
            return Collections.emptyList();
        }
        return scoreListForUserId;
    }

    @Scheduled(fixedRate = CACHE_REFRESH_TTL) // Refresh every 2 minutes
    void refreshCache() {
        getTop5ScoresFromDB(); // This will refresh the cache
        System.out.println("Cache refreshed");
    }

    public List<UserScoreDTO> getTop5ScoresFromDB_NoCache() {
        System.out.println("Cache Bypassed");
        return getTop5ScoresFromDB();
    }
}
