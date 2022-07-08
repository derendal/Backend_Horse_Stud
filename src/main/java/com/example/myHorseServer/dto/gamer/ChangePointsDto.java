package com.example.myHorseServer.dto.gamer;

import com.example.myHorseServer.model.Gamer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ChangePointsDto {
    private Gamer gamer;
    private Integer points;
}
