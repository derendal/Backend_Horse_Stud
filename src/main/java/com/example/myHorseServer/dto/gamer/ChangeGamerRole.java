package com.example.myHorseServer.dto.gamer;

import com.example.myHorseServer.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ChangeGamerRole {
    private String email;
    private Role role;
    private Role newRole;
    private Role adminRole;
    private String emailGamerToChange;
    private Role roleGamerToChange;
}
