package com.example.myHorseServer.dto.event;

import com.example.myHorseServer.model.EventList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EventListCreateResponse {
    private EventList eventList;
    private String message;
}
