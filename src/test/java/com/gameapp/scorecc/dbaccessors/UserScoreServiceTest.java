package com.gameapp.scorecc.dbaccessors;

import com.gameapp.scorecc.model.UserScoreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserScoreServiceTest {

    @Mock
    private UserScoreRepository userScoreRepository;

    @InjectMocks
    private UserScoreService userScoreService;

    @BeforeEach
    void setUp() {
        CacheManager cacheManager = new ConcurrentMapCacheManager("top5Scores");
        ReflectionTestUtils.setField(userScoreService, "userScoreRepository", userScoreRepository);
        ReflectionTestUtils.setField(userScoreService, "CACHE_REFRESH_TTL", 120000);
    }

    @Test
    void testGetTop5ScoresFromDB_WhenScoresArePresent() {
        // Arrange
        List<UserScoreDTO> mockScores = Arrays.asList(
                new UserScoreDTO(1500, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1000, new Date(1724009317374L), "luke", "342@abc", "sky")
        );
        when(userScoreRepository.findTop5Scores()).thenReturn(mockScores);

        // Act
        List<UserScoreDTO> top5Scores = userScoreService.getTop5ScoresFromDB();

        // Assert
        assertEquals(2, top5Scores.size());
        verify(userScoreRepository, times(1)).findTop5Scores();
    }

    @Test
    void testGetTop5ScoresFromDB_WhenNoScoresAreFound() {
        // Arrange
        when(userScoreRepository.findTop5Scores()).thenReturn(null);

        // Act
        List<UserScoreDTO> top5Scores = userScoreService.getTop5ScoresFromDB();

        // Assert
        assertEquals(0, top5Scores.size());
        verify(userScoreRepository, times(1)).findTop5Scores();
    }

    @Test
    void testFindScoresByUserId_WhenScoresArePresent() {
        // Arrange
        List<UserScoreDTO> mockScores = Arrays.asList(
                new UserScoreDTO(1500, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1000, new Date(1724009317374L), "luke", "342@abc", "sky")
        );
        when(userScoreRepository.findScoresByUserId(1)).thenReturn(mockScores);

        // Act
        List<UserScoreDTO> scoresByUserId = userScoreService.findScoresByUserId(1);

        // Assert
        assertEquals(2, scoresByUserId.size());
        verify(userScoreRepository, times(1)).findScoresByUserId(1);
    }

    @Test
    void testFindScoresByUserId_WhenNoScoresAreFound() {
        // Arrange
        when(userScoreRepository.findScoresByUserId(1)).thenReturn(null);

        // Act
        List<UserScoreDTO> scoresByUserId = userScoreService.findScoresByUserId(1);

        // Assert
        assertEquals(0, scoresByUserId.size());
        verify(userScoreRepository, times(1)).findScoresByUserId(1);
    }

    @Test
    void testRefreshCache() {
        // Act
        userScoreService.refreshCache();

        // Assert
        verify(userScoreRepository, times(1)).findTop5Scores();
    }

    @Test
    void testGetTop5ScoresFromDB_NoCache() {
        // Arrange
        List<UserScoreDTO> mockScores = Arrays.asList(
                new UserScoreDTO(1500, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1000, new Date(1724009317374L), "luke", "342@abc", "sky")
        );
        when(userScoreRepository.findTop5Scores()).thenReturn(mockScores);

        // Act
        List<UserScoreDTO> top5Scores = userScoreService.getTop5ScoresFromDB_NoCache();

        // Assert
        assertEquals(2, top5Scores.size());
        verify(userScoreRepository, times(1)).findTop5Scores();
    }
}
