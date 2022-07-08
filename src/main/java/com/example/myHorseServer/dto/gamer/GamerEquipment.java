package com.example.myHorseServer.dto.gamer;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class GamerEquipment {
    private Integer equipmentId;
    private Integer idItem;
    private Integer gamerId;
}
