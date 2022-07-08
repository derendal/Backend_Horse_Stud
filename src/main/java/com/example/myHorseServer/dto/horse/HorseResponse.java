package com.example.myHorseServer.dto.horse;

import com.example.myHorseServer.model.Horse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HorseResponse {
    private Horse horse;
    private String message;
}
