package com.example.myHorseServer.dto.gamer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ChangeGamerPosition {
    private String email;
    private double loc_x;
    private double new_loc_x;
    private double loc_y;
    private double new_loc_y;
    private double loc_z;
    private double new_loc_z;
    private Date lastLogout;
}
