package com.example.myHorseServer.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AuthmeDto {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private String ip;
    private Integer lastLogin;
    private double x;
    private double y;
    private double z;
    private String world;
    private Integer regdate;
    private String regip;
    private Float yaw;
    private Float pitch;
    private String email;
    private boolean gamerLogged;
    private boolean hasSession;
    private String totp;


}
