package com.example.myHorseServer.rest;

import com.example.myHorseServer.dto.event.*;
import com.example.myHorseServer.model.Event;
import com.example.myHorseServer.model.EventList;
import com.example.myHorseServer.model.EventResult;
import com.example.myHorseServer.model.EventType;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor

public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(value = "/events")
    public ResponseEntity<Iterable<Event>> getEvents(){
        System.out.println("Get events");
        return new ResponseEntity<Iterable<Event>>(eventService.findAllEvent(), HttpStatus.OK);
    }
    @GetMapping(value = "/types")
    public ResponseEntity<Iterable<EventType>> getTypes(){
        System.out.println("Get types");
        return new ResponseEntity<Iterable<EventType>>(eventService.findAllEventType(), HttpStatus.OK);
    }
    @GetMapping(value = "/results")
    public ResponseEntity<Iterable<EventResult>> getResults(){
        System.out.println("Get types");
       // eventService.checkStateResultas();
        return new ResponseEntity<Iterable<EventResult>>(eventService.findAllEventResult(), HttpStatus.OK);
    }
    @GetMapping(value="/eventbyid")
    public ResponseEntity<Event> getEventById(@RequestBody Integer eventId){
        System.out.println("Get Event by ID");
        return new ResponseEntity<Event>(eventService.findEventById(eventId),HttpStatus.OK);
    }
    @GetMapping(value="/typebyid")
    public ResponseEntity<EventType> getEventTypeById(@RequestBody Integer eventTypeId){
        System.out.println("Get Event Type by ID");
        return new ResponseEntity<EventType>(eventService.loadEventTypeById(eventTypeId),HttpStatus.OK);
    }

    @GetMapping(value="/typebyname")
    public ResponseEntity<EventType> getEventTypeByName(@RequestBody String eventTypename){
        System.out.println("Get Event Type by name");
        return new ResponseEntity<EventType>(eventService.loadEventTypeByName(eventTypename),HttpStatus.OK);
    }

    @GetMapping(value="/resultbyid")
    public ResponseEntity<EventResult> getEventResultById(@RequestBody Integer eventResultId){
        System.out.println("Get Event Result by ID");
        return new ResponseEntity<EventResult>(eventService.loadEventResultById(eventResultId),HttpStatus.OK);
    }

    @GetMapping(value = "/eventlistid")
    public ResponseEntity<?> getEventList(@RequestBody Integer eventListId){
        System.out.println("Get event list by ID");
        return new ResponseEntity<>(eventService.findAllOfEventList(eventListId), HttpStatus.OK);
    }

    @GetMapping(value = "/alleventlist")
    public ResponseEntity<Iterable<EventList>> getbyEventListId(@AuthenticationPrincipal Gamer gamer){
        System.out.println("Get All Event List ");

        return new ResponseEntity<Iterable<EventList>>(eventService.findAllEventList(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/eventlistgamer")
    public ResponseEntity<Iterable<EventList>> getEventList(@AuthenticationPrincipal Gamer gamer){
        System.out.println("Get event list by ID");
        return new ResponseEntity<Iterable<EventList>>(eventService.findAllGamerEventList(gamer.getGamerId()), HttpStatus.OK);
    }


    @PutMapping(value = "/change/event" )
    public ResponseEntity<?> changeEvent(@AuthenticationPrincipal Gamer gamer,@RequestBody Event event){
        System.out.println("Change event");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            eventService.changeEvent(event);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
    @PutMapping(value = "/change/eventresult")
    public ResponseEntity<?> changeEventResult(@AuthenticationPrincipal Gamer gamer,@RequestBody EventResult eventResult){
        System.out.println("Change event result");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            eventService.chanegeEventResult(eventResult);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
    @PutMapping(value = "/change/eventtype")
    public ResponseEntity<?> changeEventType(@AuthenticationPrincipal Gamer gamer,@RequestBody EventType eventType){
        System.out.println("Change event type");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            eventService.changeEventType(eventType);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
    @DeleteMapping(value = "/delete/{eventid}")
    public ResponseEntity<EventDeleteResponse> deleteEventList(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer eventid) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(eventService.deleteEvent(eventid));
        }
        return ResponseEntity.badRequest().body(new EventDeleteResponse(null, "Forbidden"));
    }

    @DeleteMapping(value = "/delete/{eventlist}")
    public ResponseEntity<EventListDeleteResponse> deleteEvent(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer eventid) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(eventService.deleteEventList(eventid));
        }
        return ResponseEntity.badRequest().body(new EventListDeleteResponse(null, "Forbidden"));
    }

    @DeleteMapping(value = "/delete/{eventtype}")
    public ResponseEntity<EventTypeDeleteResponse> deleteEventType(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer eventTypeId) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(eventService.deleteEventType(eventTypeId));
        }
        return ResponseEntity.badRequest().body(new EventTypeDeleteResponse(null, "Forbidden"));
    }
    @DeleteMapping(value = "/delete/{eventresult}")
    public ResponseEntity<EventResultDeleteResponse> deleteEventResult(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer eventResultId) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(eventService.deleteEventResult(eventResultId));
        }
        return ResponseEntity.badRequest().body(new EventResultDeleteResponse(null, "Forbidden"));
    }

    @CrossOrigin
    @PostMapping(value = "/new/eventlist")
    public ResponseEntity<EventListCreateResponse> createEventList( @RequestBody EventList eventList){
        EventListCreateResponse eventListCreateResponse = eventService.createEventList(eventList);
        System.out.println("--- New event created ---");
            if (eventListCreateResponse.getMessage().equals("Create new event list - succesfull")) {
                return new ResponseEntity<>(eventListCreateResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(eventListCreateResponse, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping(value = "/new/event")
    public ResponseEntity<EventCreateResponse> createEvent(@AuthenticationPrincipal Gamer gamer, @RequestBody Event event){
        EventCreateResponse eventCreateResponse = eventService.createEvent(event);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (eventCreateResponse.getMessage().equals("Create new event - successfull")) {
                return new ResponseEntity<>(eventCreateResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(eventCreateResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<EventCreateResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @CrossOrigin
    @PostMapping(value = "/new/eventtype")
    public ResponseEntity<EventTypeCreateResponse> createEventType(@AuthenticationPrincipal Gamer gamer, @RequestBody EventType eventType){
        EventTypeCreateResponse eventTypeCreateResponse = eventService.createEventType(eventType);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (eventTypeCreateResponse.getMessage().equals("Create new event type - successfull")) {
                return new ResponseEntity<>(eventTypeCreateResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(eventTypeCreateResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<EventTypeCreateResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @CrossOrigin
    @PostMapping(value = "/new/eventresult")
    public ResponseEntity<EventResultCreateResponse> createEventResult(@AuthenticationPrincipal Gamer gamer, @RequestBody EventResult eventResult){
        EventResultCreateResponse eventResultCreateResponse = eventService.createEventResult(eventResult);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (eventResultCreateResponse.getMessage().equals("Create new event result - successfull")) {
                return new ResponseEntity<>(eventResultCreateResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(eventResultCreateResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<EventResultCreateResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
}
