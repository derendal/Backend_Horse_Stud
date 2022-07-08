package com.example.myHorseServer.dto.gamerStud;


import com.example.myHorseServer.dto.gamer.GamerDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GamerStud {
    private  Integer gamerStudId;
    private GamerDataDto gamerId;
    private String studName;
}
