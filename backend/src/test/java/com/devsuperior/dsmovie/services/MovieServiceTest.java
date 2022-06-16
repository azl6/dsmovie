package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entity.Movie;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieService movieService;

    @Mock
    MovieRepository movieRepository;

    Page<Movie> moviesPage;

    Movie movie1;
    Movie movie2;

    @BeforeEach
    void setUp() {
        movie1 = new Movie(1L, "Movie 1", 2.5, 3, null);
        movie2 = new Movie(2L, "Movie 2", 4.0, 2, null);

        //criando um Page que guarda objetos, número de páginas  e total de elementos
        moviesPage = new PageImpl<>(List.of(movie1, movie2), PageRequest.of(1, 2), 2);
    }

    @Test
    void findAll() {
        //chamar as funções da camada mais inferior para a mais superior

        //given - dado uma condição
        given(movieRepository.findAll((Pageable) any())).willReturn(moviesPage);

        //when - quando tal condição acontecer, guarda o resultado de acordo com o retorno do método
        Page<MovieDTO> moviesPageDto = movieService.findAll(moviesPage.getPageable());

        //then - então garanta que os os resultados foram iguais
        then(movieRepository).should(times(1)).findAll(moviesPage.getPageable());

        //verificando se, o tamanho da lista será de 2 elementos
        assertEquals(2, moviesPageDto.getSize());
        assertEquals("Movie 2", moviesPageDto.getContent().get(1).getTitle());
    }

    @Test
    void findById() {
        //given
        given(movieRepository.findById(movie1.getId())).willReturn(Optional.of(movie1)); //não retorna um Optional

        //when
        MovieDTO movieResult = movieService.findById(movie1.getId());

        //then
        then(movieRepository).should(times(1)).findById(movie1.getId());
        assertEquals(1L, movieResult.getId());
        assertEquals("Movie 1", movieResult.getTitle());
    }
}