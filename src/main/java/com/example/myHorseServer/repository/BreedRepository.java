package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.Breed;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {

    @Cacheable
    Optional<Breed> findByBreedId(Integer breedId);
}
