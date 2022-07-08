package com.example.myHorseServer.repository;


import com.example.myHorseServer.model.EventList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EventListRepository  extends JpaRepository<EventList, Integer> {

    @Cacheable
    Optional<EventList> findByEventListId(Integer eventListId);

    @Query(value = "SELECT * FROM event_list AS t WHERE t.event_id = :eventId", nativeQuery = true)
     Optional<EventList> findAllById (@Param("eventId") Integer eventId);

    @Query(value = "SELECT * FROM event_list AS t WHERE t.gamer_id = :gamerId", nativeQuery = true)
    Iterable<EventList> findAllByGamerId (@Param("gamerId") Integer gamerId);
}
