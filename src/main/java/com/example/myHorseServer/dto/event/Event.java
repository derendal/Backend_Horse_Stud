package com.example.myHorseServer.dto.event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Event {
    private Integer eventId;
    private EventType eventType;
    private Date date;
    private boolean isEnd;
}


























