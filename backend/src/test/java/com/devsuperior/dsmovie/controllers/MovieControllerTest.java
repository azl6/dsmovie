package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entity.Movie;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    Pageable pageable = PageRequest.of(1, 2);
    List<Movie> movieList;
    Page<Movie> moviePage;
    Page<MovieDTO> moviePageDto;
    MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movieList =
                List.of(new Movie(1L, "Movie 1", 4.0, 2, null),
                        new Movie(2L, "Movie 2", 3.3, 3, null));

        moviePage = new PageImpl<>(movieList, pageable, 1);
        moviePageDto = moviePage.map(x -> MovieDTO.builder()
                .id(x.getId())
                .title(x.getTitle())
                .score(x.getScore())
                .count(x.getCount())
                .image(x.getImage())
                .build());

        movieDTO = MovieDTO.builder().id(3L).title("Movie 3").build();
    }

    @AfterEach
    void tearDown() {
        reset(movieService);
    }

    @Test
    void findAll() throws Exception {
        //given
        given(movieService.findAll(pageable)).willReturn(moviePageDto);

        //when - then
        mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .param("page", String.valueOf(1))
                        .param("size", String.valueOf(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title", is("Movie 1")));
    }

    @Test
    void findById() throws Exception {
        //given
        given(movieService.findById(movieDTO.getId())).willReturn(movieDTO);

        //when - then
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + 3L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title", is("Movie 3")));
    }
}