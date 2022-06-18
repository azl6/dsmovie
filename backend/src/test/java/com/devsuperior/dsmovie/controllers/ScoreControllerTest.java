package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.services.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({ScoreController.class})
class ScoreControllerTest {

    @MockBean
    ScoreService scoreService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ScoreDTO scoreDTO;
    MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movieDTO = MovieDTO.builder().id(1L).title("Movie 1").score(4.0).count(1).image(null).build();

        scoreDTO = new ScoreDTO();
        scoreDTO.setMovieId(1L);
        scoreDTO.setEmail("bruna@gmail.com");
        scoreDTO.setScore(2.5);
    }

    @Test
    void saveScore() throws Exception {

        //given
        given(scoreService.saveScore(scoreDTO)).willReturn(movieDTO);

        //when - then
        mockMvc.perform(MockMvcRequestBuilders.put("/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreDTO)))

                .andExpect(status().isOk());
    }
}