package com.gameapp.scorecc.controllers;

import com.gameapp.scorecc.dbaccessors.UserScoreService;
import com.gameapp.scorecc.model.UserScoreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Top5ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserScoreService userScoreService;

    @InjectMocks
    private Top5Controller top5Controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(top5Controller).build();
    }

    @Test
    void testGetTop5Scores() throws Exception {
        List<UserScoreDTO> mockScores = Arrays.asList(
                new UserScoreDTO(1500, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1000, new Date(1724009317374L), "luke", "342@abc", "sky")
        );

        when(userScoreService.getTop5ScoresFromDB()).thenReturn(mockScores);

        mockMvc.perform(get("/scores/top5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].scoreValue").value(1500))
                .andExpect(jsonPath("$[0].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[0].name").value("Hello"))
                .andExpect(jsonPath("$[0].email").value("123@abc"))
                .andExpect(jsonPath("$[0].userName").value("haiho"))
                .andExpect(jsonPath("$[1].scoreValue").value(1000))
                .andExpect(jsonPath("$[1].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[1].name").value("luke"))
                .andExpect(jsonPath("$[1].email").value("342@abc"))
                .andExpect(jsonPath("$[1].userName").value("sky"));
    }

    @Test
    void testGetTop5ScoresAccurate() throws Exception {
        List<UserScoreDTO> mockScores = Arrays.asList(
                new UserScoreDTO(1500, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1000, new Date(1724009317374L), "luke", "342@abc", "sky")
        );

        when(userScoreService.getTop5ScoresFromDB_NoCache()).thenReturn(mockScores);

        mockMvc.perform(get("/scores/top5_accurate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].scoreValue").value(1500))
                .andExpect(jsonPath("$[0].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[0].name").value("Hello"))
                .andExpect(jsonPath("$[0].email").value("123@abc"))
                .andExpect(jsonPath("$[0].userName").value("haiho"))
                .andExpect(jsonPath("$[1].scoreValue").value(1000))
                .andExpect(jsonPath("$[1].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[1].name").value("luke"))
                .andExpect(jsonPath("$[1].email").value("342@abc"))
                .andExpect(jsonPath("$[1].userName").value("sky"));
    }

    @Test
    void testGetTop5ScoresWithUser() throws Exception {
        List<UserScoreDTO> mockTop5Scores = Arrays.asList(
                new UserScoreDTO(1000, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1500, new Date(1724009317374L), "luke", "342@abc", "sky")
        );

        List<UserScoreDTO> mockUserScores = Arrays.asList(
                new UserScoreDTO(3000, new Date(1724009317374L), "od", "321@abc", "id")
        );

        when(userScoreService.getTop5ScoresFromDB()).thenReturn(mockTop5Scores);
        when(userScoreService.findScoresByUserId(3)).thenReturn(mockUserScores);

        mockMvc.perform(get("/scores/top5/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].scoreValue").value(3000))
                .andExpect(jsonPath("$[0].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[0].name").value("od"))
                .andExpect(jsonPath("$[0].email").value("321@abc"))
                .andExpect(jsonPath("$[0].userName").value("id"))
                .andExpect(jsonPath("$[1].scoreValue").value(1500))
                .andExpect(jsonPath("$[1].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[1].name").value("luke"))
                .andExpect(jsonPath("$[1].email").value("342@abc"))
                .andExpect(jsonPath("$[1].userName").value("sky"))
                .andExpect(jsonPath("$[2].scoreValue").value(1000))
                .andExpect(jsonPath("$[2].scoreDate").value("1724009317374"))
                .andExpect(jsonPath("$[2].name").value("Hello"))
                .andExpect(jsonPath("$[2].email").value("123@abc"))
                .andExpect(jsonPath("$[2].userName").value("haiho"));
    }

    @Test
    void testGetTop5ScoresWithUser_moreThan5() throws Exception {
        List<UserScoreDTO> mockTop5Scores = Arrays.asList(
                new UserScoreDTO(1000, new Date(1724009317374L), "Hello", "123@abc", "haiho"),
                new UserScoreDTO(1500, new Date(1724009317374L), "luke", "342@abc", "poker"),
                new UserScoreDTO(5000, new Date(1724009317374L), "Hello", "123@abc", "jimmy"),
                new UserScoreDTO(4500, new Date(1724009317374L), "luke", "342@abc", "jhonny")
        );

        List<UserScoreDTO> mockUserScores = Arrays.asList(
                new UserScoreDTO(3000, new Date(1724009317374L), "od", "321@abc", "joker"),
                new UserScoreDTO(3500, new Date(1724009317374L), "od", "321@abc", "jacky")
        );

        when(userScoreService.getTop5ScoresFromDB()).thenReturn(mockTop5Scores);
        when(userScoreService.findScoresByUserId(3)).thenReturn(mockUserScores);

        mockMvc.perform(get("/scores/top5/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userName").value("jimmy"))
                .andExpect(jsonPath("$[1].userName").value("jhonny"))
                .andExpect(jsonPath("$[2].userName").value("jacky"))
                .andExpect(jsonPath("$[3].userName").value("joker"))
                .andExpect(jsonPath("$[4].userName").value("poker"));
    }

    @Test
    void testGetTop5ScoresWithUser_ThrowsException() throws Exception {
        when(userScoreService.getTop5ScoresFromDB()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            top5Controller.getTop5Scores(3);
        });

        assert(exception.getCause().getMessage().equals("Database error"));
    }

    @Test
    void testGetTop5Scores_ThrowsException() throws Exception {
        when(userScoreService.getTop5ScoresFromDB()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            top5Controller.getTop5Scores();
        });

        assert(exception.getCause().getMessage().equals("Database error"));
    }

    @Test
    void testGetTop5ScoresAccurate_ThrowsException() throws Exception {
        when(userScoreService.getTop5ScoresFromDB_NoCache()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            top5Controller.getTop5ScoresAccurate();
        });

        assert(exception.getCause().getMessage().equals("Database error"));
    }
}
