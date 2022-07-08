package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.EventType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {

    @Cacheable
    Optional<EventType> findByEventTypeId(Integer eventTypeId);

    @Cacheable
    Optional<EventType> findByName(String eventTypeName);

}
