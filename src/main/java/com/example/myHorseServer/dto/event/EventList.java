package com.example.myHorseServer.dto.event;

import com.example.myHorseServer.dto.horse.Horse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EventList {
    private Integer eventListId;
    private Event event;
    private Integer gamerId;
    private Horse horse;
}
