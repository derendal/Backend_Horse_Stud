package com.example.myHorseServer.dto.gamerStud;

import com.example.myHorseServer.model.GamerStud;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GamerStudDeleteResponse {
    private GamerStud gamerStudData;
    private String message;
}
