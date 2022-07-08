package com.example.myHorseServer.dto.horse;

import com.example.myHorseServer.model.Breed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BreedResponse {
    private Breed breed;
    private String message;
}
