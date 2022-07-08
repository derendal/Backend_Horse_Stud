package com.example.myHorseServer.dto.gamer;

import lombok.Data;

@Data
public class ChangePasswordDto {

    private String email;
    private String oldPassword;
    private String newPassword;

}
