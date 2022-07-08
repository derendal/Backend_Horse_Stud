package com.example.myHorseServer.repository;

import com.example.myHorseServer.model.Event;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EventRepository  extends JpaRepository <Event, Integer>{

    @Cacheable
    Optional<Event> findByEventId(Integer eventId);

    @Query(value = "SELECT * FROM `event` WHERE `date` < NOW() AND `is_end` = FALSE", nativeQuery=true)
    List<Event> findNotFinshed();
}
