package com.example.myHorseServer.dto.gamer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GamerRegisterResponse {
    private GamerDataDto gamer;
    private String message;

    @Override
    public boolean equals(Object o){
        if(o instanceof GamerRegisterResponse){
            GamerRegisterResponse other = (GamerRegisterResponse) o;
            return message.equals(other.message) && gamer.equals(other.gamer);
        }
        return false;
    }
}
