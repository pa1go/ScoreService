package com.gameapp.scorecc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserScoreTest {

    private UserScore userScore;
    private User user;

    @BeforeEach
    void setUp() {
        userScore = new UserScore();
        user = new User(); // Assuming User is a simple POJO
    }

    @Test
    void testInitialValues() {
        assertNull(userScore.getScoreId(), "ScoreId should be null initially");
        assertNull(userScore.getUser(), "User should be null initially");
        assertNull(userScore.getScoreValue(), "ScoreValue should be null initially");
        assertNull(userScore.getScoreDate(), "ScoreDate should be null initially");
    }

    @Test
    void testSettersAndGetters() {
        userScore.setScoreId(1);
        userScore.setUser(user);
        userScore.setScoreValue(100);
        userScore.setScoreDate(Date.valueOf("2024-08-19"));

        assertEquals(1, userScore.getScoreId(), "ScoreId should be 1");
        assertEquals(user, userScore.getUser(), "User should be set correctly");
        assertEquals(100, userScore.getScoreValue(), "ScoreValue should be 100");
        assertEquals(Date.valueOf("2024-08-19"), userScore.getScoreDate(), "ScoreDate should be set correctly");
    }
}
