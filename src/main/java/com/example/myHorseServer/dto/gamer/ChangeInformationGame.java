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

public class ChangeInformationGame {
    private String emial;
    private Date lastLogin;
    private Date newlastLogin;
    private Date lastLogout;
    private Date newlastLogout;
    private Integer spendTime;
    private Integer newspendTime;
}
