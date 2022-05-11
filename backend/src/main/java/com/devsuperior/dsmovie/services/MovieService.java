package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entity.Movie;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    //Utilização do Pageable no findAll()
    @Transactional(readOnly = true) //readOnly para aumentar eficiência no DB
    public Page<MovieDTO> findAll(Pageable pageable){
            Page<Movie> movieList = movieRepository.findAll(pageable);
        return movieList.map(movie -> new MovieDTO(movie));
    }

    @Transactional(readOnly = true) //readOnly para aumentar eficiência no DB
    public MovieDTO findById(Long id){
        Movie movie = movieRepository.findById(id).get();
        return new MovieDTO(movie);
    }
}
