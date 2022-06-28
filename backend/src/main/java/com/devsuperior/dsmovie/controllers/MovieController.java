package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
@NoArgsConstructor
public class MovieController {

    @Autowired
    private MovieService movieService;

    //Resposta paginada
    @GetMapping
    public Page<MovieDTO> findAll(@RequestParam("page") int pageIndex,
                                  @RequestParam("size") int pageSize){
        return movieService.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @GetMapping("/{id}")
    public MovieDTO findById(@PathVariable Long id){
        return movieService.findById(id);
    }
}
