package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.Authme;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthmeRepository extends JpaRepository<Authme, Integer> {

    @Cacheable
    Optional<Authme> findById(Integer id);

    @Cacheable
    Optional<Authme> findByUsername(String nickname);
}
