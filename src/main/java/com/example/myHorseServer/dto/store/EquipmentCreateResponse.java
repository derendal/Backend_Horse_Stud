package com.example.myHorseServer.dto.store;

import com.example.myHorseServer.model.GamerEquipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EquipmentCreateResponse {
    private GamerEquipment gamerEquipment;
    private String message;
}
