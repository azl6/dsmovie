package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entity.Movie;
import com.devsuperior.dsmovie.entity.Score;
import com.devsuperior.dsmovie.entity.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ScoreServiceTest {

    @InjectMocks
    ScoreService scoreService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ScoreRepository scoreRepository;

    ScoreDTO scoreDTO = new ScoreDTO();
    Score score = new Score();
    User user = new User();
    Movie movie = new Movie();

    @BeforeEach
    void setUp() {
        scoreDTO.setMovieId(1L);
        scoreDTO.setScore(4.5);
        scoreDTO.setEmail("alex@gmail.com");


        user.setId(1L);
        user.setEmail(scoreDTO.getEmail());

        movie.setId(1L);
        movie.setTitle("Movie 1");
        movie.setScore(2.5);
        movie.setCount(2);
        movie.setImage(null);

        score.setMovie(movie);
        score.setUser(user);
        score.setValue(4.5);

    }

    @Test
    void saveScoreWhenUserIsNotNull() {
        //given
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);
        given(movieRepository.findById(scoreDTO.getMovieId())).willReturn(Optional.of(movie));
        given(scoreRepository.save(score)).willReturn(score);
        given(movieRepository.save(movie)).willReturn(movie);

        //when
        MovieDTO result = scoreService.saveScore(scoreDTO);

        //then
        then(userRepository).should(times(1)).findByEmail(user.getEmail());
        then(userRepository).should(times(0)).saveAndFlush(any());
        then(movieRepository).should(times(1)).findById(scoreDTO.getMovieId());
        then(scoreRepository).should(times(1)).save(score);
        then(movieRepository).should(times(1)).save(movie);


        assertEquals("Movie 1", result.getTitle());
        assertEquals(null, result.getImage());
    }
}