package com.devsuperior.dsmovie.config;

import com.devsuperior.dsmovie.services.MovieService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    MovieService movieService(){
        return new MovieService();
    }
}
