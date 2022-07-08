package com.example.myHorseServer.dto;

import com.example.myHorseServer.model.Gamer;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Plot {
    private Integer plotId;
    private Gamer gamer;
    private double x;
    private double y;
    private double z;
}
