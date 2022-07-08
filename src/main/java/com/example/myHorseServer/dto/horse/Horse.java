package com.example.myHorseServer.dto.horse;

import com.example.myHorseServer.dto.gamerStud.GamerStud;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Horse {
    private Integer horseId;
    private String bukkitHorseId;
    private GamerStud gamerStudId;
    private String name;
    private Breed breedId;
    private double fast;
    private double hungry;
    private double thirst;
    private double appearance;
    private double value;


}
