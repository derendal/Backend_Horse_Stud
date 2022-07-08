package com.example.myHorseServer.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EventType {
    private Integer EventTypeId;
    private String name;
    private String description;
    private Integer pointsScored;
}
