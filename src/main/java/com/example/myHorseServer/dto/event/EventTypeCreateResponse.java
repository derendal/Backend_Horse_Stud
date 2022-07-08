package com.example.myHorseServer.dto.event;

import com.example.myHorseServer.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EventTypeCreateResponse {
    private EventType eventType;
    private String message;
}
