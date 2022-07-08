package com.example.myHorseServer.dto.gamer;
import com.example.myHorseServer.model.Role;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class GamerDataDto {
    private Integer gamerId;
    private Integer authmeId;
    private String nickname;
    private Integer points;
    private Date lastLogin;
    private Date lastLogout;
    private Integer spendTime;
    private Role role;
    private double loc_x;
    private double loc_y;
    private double loc_z;
    private String email;
    private String password;


    @Override
    public boolean equals(Object o){
        if(o instanceof GamerDataDto){
            GamerDataDto other = (GamerDataDto) o;
            return Objects.equals(gamerId,other.gamerId) && Objects.equals(nickname,other.nickname)
                    && Objects.equals(points,other.points) && Objects.equals(lastLogin,other.lastLogin)
                    && Objects.equals(lastLogout,other.lastLogout) && Objects.equals(spendTime,other.spendTime)
                    && loc_x==(other.loc_x) && loc_y==(other.loc_y)
                    && loc_z==(other.loc_z) && Objects.equals(email,other.email)
                    && Objects.equals(password,other.password) ;
        }
        return false;
    }
}
