package com.example.myHorseServer.service;


import ch.qos.logback.classic.pattern.DateConverter;
import com.example.myHorseServer.dto.event.*;
import com.example.myHorseServer.dto.gamer.ChangePointsDto;
import com.example.myHorseServer.exception.EventNotFoundException;
import com.example.myHorseServer.model.*;
import com.example.myHorseServer.model.Event;
import com.example.myHorseServer.model.EventList;
import com.example.myHorseServer.model.EventResult;
import com.example.myHorseServer.model.EventType;
import com.example.myHorseServer.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;

@Service
public class EventService {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventResultRepository eventResultRepository;

    @Autowired
    private EventListRepository eventListRepository;

    @Autowired
    private GamerRepository gamerRepository;

    @Autowired
    private GamerService gamerService;

    @Autowired
    private EventResultRepositoryWinner eventResultRepositoryWinner;

    @Autowired
    private HorseRepository horseRepository;


    public EventCreateResponse createEvent(Event event){
        Event creator = new Event();
        creator.setEventType(event.getEventType());
        creator.setDate(event.getDate());
        creator = eventRepository.save(creator);

        return new EventCreateResponse(new Event(
                creator.getEventId(),
                creator.getDate(),
                creator.getEventType(),
                creator.isEnd()
                ),"Create new event - successfull");
    }

    public EventListCreateResponse createEventList(EventList eventList){
        EventList creator = new EventList();
        creator.setEventListId(eventList.getEventListId());
        creator.setEvent(eventList.getEvent());
        creator.setHorse(eventList.getHorse());
        creator.setGamer(eventList.getGamer());
        creator = eventListRepository.save(creator);
        return new EventListCreateResponse(new EventList(
                creator.getEventListId(),
                creator.getEvent(),
                creator.getHorse(),
                creator.getGamer()
                ),"Create new event list - succesfull");
    }
    public EventTypeCreateResponse createEventType(EventType eventType){
        EventType creator = new EventType();
        creator.setName(eventType.getName());
        creator.setDescription(eventType.getDescription());
        creator.setPointsScored(eventType.getPointsScored());
        creator = eventTypeRepository.save(creator);

        return new EventTypeCreateResponse(new EventType(
               creator.getEventTypeId(),
                creator.getName(),
                creator.getDescription(),
                creator.getPointsScored()
        ),"Create new event type - successfull");
    }

    public EventResultCreateResponse createEventResult(EventResult eventResult){
        EventResult creator = new EventResult();
        creator.setHorseId(eventResult.getHorseId());
        creator.setPointsScored(eventResult.getPointsScored());
        creator = eventResultRepository.save(creator);

        return new EventResultCreateResponse(new EventResult(
                creator.getEventResultId(),
                creator.getHorseId(),
                creator.getPointsScored(),
                creator.getEventId()
                ),"Create new event result - successfull");
    }

    public Iterable<Event> findAllEvent(){
        return eventRepository.findAll();
    }

    public Iterable<EventType> findAllEventType(){
        return eventTypeRepository.findAll();
    }

    public Iterable<EventResult> findAllEventResult(){
        return eventResultRepository.findAll();
    }

    @Transactional
    public void checkStateResultas(int eventId){
        List<EventWinner> winners = eventResultRepositoryWinner.getWinnersOfEvent(eventId);

        for(EventWinner winner : winners) {
            //System.out.println("SPRAWDZAMY: " + winner.getEventId()+ " " + winner.getHorseId() + " " + winner.getPointsScored());
            Event event = eventRepository.getById(winner.getEventId());
            Horse horse = horseRepository.getById(winner.getHorseId());

            Gamer gamer = horse.getGamerStud().getGamerId();
            gamer.setPoints(gamer.getPoints() + event.getEventType().getPointsScored());
            gamerRepository.save(gamer);

            EventResult result = new EventResult();
            result.setEventId(event);
            result.setHorseId(horse);
            result.setPointsScored(winner.getPointsScored());
            eventResultRepository.save(result);
        }
    }

    public EventList findAllOfEventList(Integer eventListId){return  eventListRepository.findAllById(eventListId).orElse(null);}

    public Iterable<EventList> findAllEventList(){return eventListRepository.findAll();}

    public Iterable<EventList> findAllGamerEventList(Integer gamerListId){
        return  eventListRepository.findAllByGamerId(gamerListId);
    }


    public Event findEventById(Integer eventId){
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(format("Event with id - %s, not found", eventId))
        );
    }

    public EventType loadEventTypeById(Integer eventTypeId){
        return eventTypeRepository.findByEventTypeId(eventTypeId).orElseThrow(()-> new EventNotFoundException(format("Event type - %s, not found",eventTypeId)));
    }

    public EventType loadEventTypeByName(String eventTypeName){
        return eventTypeRepository.findByName(eventTypeName).orElseThrow(()-> new EventNotFoundException(format("Event type - %s, not found",eventTypeName)));
    }

    public EventResult loadEventResultById(Integer eventResultId){
        return eventResultRepository.findByEventResultId(eventResultId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventResultId)));
    }

    public EventList loadEventListById(Integer eventListId){
        return eventListRepository.findByEventListId(eventListId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventListId)));
    }

    public void changeEvent(Event eventChange){
        Event event = eventRepository.findById(eventChange.getEventId()).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventChange.getEventType())));
       if(eventChange.getEventType()!=null || event.getDate()!=null){
           if (eventChange.getEventType()!=null){
               event.setEventType(eventChange.getEventType());
           }if(event.getDate()!=null){
               event.setDate(eventChange.getDate());
           }
           eventRepository.save(event);
       }else throw new RuntimeException("Brak zmian");
    }

    public void chanegeEventResult(EventResult eventResultChange){
        EventResult eventResult = eventResultRepository.findByEventResultId(eventResultChange.getEventResultId()).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventResultChange.getEventResultId())));

        if (eventResultChange.getHorseId()!=null || eventResultChange.getHorseId() != eventResult.getHorseId() ){
            eventResult.setHorseId(eventResultChange.getHorseId());
            eventResultRepository.save(eventResult);
        }else if(eventResultChange.getPointsScored()!=null || eventResultChange.getPointsScored()!=eventResult.getPointsScored()){
            eventResult.setPointsScored(eventResultChange.getPointsScored());
            eventResultRepository.save(eventResult);
        }else throw new RuntimeException("No changes");
    }

    public void changeEventType(EventType eventTypeChange){
        EventType eventType = eventTypeRepository.findByEventTypeId(eventTypeChange.getEventTypeId()).orElseThrow(()->new EventNotFoundException(format("Event type - %s, not found",eventTypeChange.getName())));

        if(!eventTypeChange.equals(eventType)){
            if(!eventTypeChange.getName().equals(eventType.getName()) || eventTypeChange.getName()!=null){
                eventType.setName(eventTypeChange.getName());
            }else throw new RuntimeException("No changes in name");
            if(!eventTypeChange.getPointsScored().equals(eventType.getPointsScored()) || eventTypeChange.getPointsScored()!=null){
                eventType.setPointsScored(eventTypeChange.getPointsScored());
            }else throw new RuntimeException("No changes in points");
            if(!eventTypeChange.getDescription().equals(eventType.getDescription()) || eventTypeChange.getDescription()!=null){
                eventType.setDescription(eventTypeChange.getDescription());
            }else throw new RuntimeException("No changes in description");
        }else throw new RuntimeException("No changes in event type");
        eventTypeRepository.save(eventType);
    }

    public EventDeleteResponse deleteEvent(Integer eventId){
        Event eventDelete = eventRepository.findById(eventId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventId)));
        eventRepository.deleteById(eventId);

        return new EventDeleteResponse(new Event(
                        eventDelete.getEventId(),
                eventDelete.getDate(),
                eventDelete.getEventType(),
                eventDelete.isEnd()
                ),"Deleted event successfull");
    }

    public EventTypeDeleteResponse deleteEventType(Integer eventTypeId){
        EventType eventTypeDelete = eventTypeRepository.findByEventTypeId(eventTypeId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventTypeId)));
        eventTypeRepository.deleteById(eventTypeId);

        return new EventTypeDeleteResponse(new EventType(
                eventTypeDelete.getEventTypeId(),
                eventTypeDelete.getName(),
                eventTypeDelete.getDescription(),
                eventTypeDelete.getPointsScored()
        ),"Deleted event type successfull");
    }

    public EventResultDeleteResponse deleteEventResult(Integer eventResultId){
        EventResult eventResultDelete = eventResultRepository.findByEventResultId(eventResultId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventResultId)));
        eventResultRepository.deleteById(eventResultId);

        return new EventResultDeleteResponse(new EventResult(
                eventResultDelete.getEventResultId(),
                eventResultDelete.getHorseId(),
                eventResultDelete.getPointsScored(),
                eventResultDelete.getEventId()

                        ),"Deleted event results successfull");
    }

    public EventListDeleteResponse deleteEventList(Integer eventlistid) {
        EventList eventListDelete = eventListRepository.findByEventListId(eventlistid).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",eventlistid)));
        eventListRepository.deleteById(eventlistid);

        return new EventListDeleteResponse(new EventList(
                eventListDelete.getEventListId(),
                eventListDelete.getEvent(),
                eventListDelete.getHorse(),
                eventListDelete.getGamer()
        ),"Deleted event results successfull");
    }


}
