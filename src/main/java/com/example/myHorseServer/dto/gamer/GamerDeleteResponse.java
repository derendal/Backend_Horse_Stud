package com.example.myHorseServer.dto.gamer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GamerDeleteResponse {
    private GamerDataDto gamer;
    private String message;

    @Override
    public boolean equals(Object o){
        if(o instanceof GamerDeleteResponse){
            GamerDeleteResponse other = (GamerDeleteResponse) o;
            return message.equals(other.message) && gamer.equals(other.gamer);
        }
        return false;
    }
}
