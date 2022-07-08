package com.example.myHorseServer.dto.horse;

import com.example.myHorseServer.model.Breed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class BreedDeleteResponse {
    private Breed breed;
    private String message;
}
