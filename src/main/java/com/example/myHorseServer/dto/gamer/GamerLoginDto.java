package com.example.myHorseServer.dto.gamer;

import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class GamerLoginDto {
    private String email;
    private String password;

}
