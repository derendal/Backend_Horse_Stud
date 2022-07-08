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

public class EventResult {
    private Integer eventResultId;
    private Horse horseId;
    private Integer pointsScored;
    private Event eventId;
}
