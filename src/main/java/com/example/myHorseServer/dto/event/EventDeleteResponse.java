package com.example.myHorseServer.dto.event;

import com.example.myHorseServer.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EventDeleteResponse {
    private Event event;
    private String message;
}
