package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.Gamer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GamerRepository extends JpaRepository<Gamer,Integer> {

    @Cacheable
    Optional<Gamer> findByEmail(String email);
}
