package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Horse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface HorseRepository extends JpaRepository<Horse, Integer> {

    @Cacheable
    Optional<Horse> findByHorseId(Integer horseId);


}
