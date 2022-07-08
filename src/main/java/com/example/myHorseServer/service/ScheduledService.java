package com.example.myHorseServer.service;

import com.example.myHorseServer.model.Event;
import com.example.myHorseServer.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service

public class ScheduledService {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 1000)
    public void scheduleFixedDelayTask() {

        for(Event event : eventRepository.findNotFinshed()) {
            eventService.checkStateResultas(event.getEventId());
            event.setEnd(true);
            eventRepository.save(event);
        }
    }
}
