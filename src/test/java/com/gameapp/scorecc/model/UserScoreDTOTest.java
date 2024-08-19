package com.gameapp.scorecc.model;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserScoreDTOTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        Integer expectedScoreValue = 100;
        Date expectedScoreDate = Date.valueOf("2024-08-19");
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@example.com";
        String expectedUserName = "johndoe";

        // Act
        UserScoreDTO userScoreDTO = new UserScoreDTO(expectedScoreValue, expectedScoreDate, expectedName, expectedEmail, expectedUserName);

        // Assert
        assertEquals(expectedScoreValue, userScoreDTO.getScoreValue());
        assertEquals(expectedScoreDate, userScoreDTO.getScoreDate());
        assertEquals(expectedName, userScoreDTO.getName());
        assertEquals(expectedEmail, userScoreDTO.getEmail());
        assertEquals(expectedUserName, userScoreDTO.getUserName());
    }
}
