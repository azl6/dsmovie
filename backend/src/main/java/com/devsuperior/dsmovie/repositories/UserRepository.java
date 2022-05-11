package com.devsuperior.dsmovie.repositories;

import com.devsuperior.dsmovie.entity.Movie;
import com.devsuperior.dsmovie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
