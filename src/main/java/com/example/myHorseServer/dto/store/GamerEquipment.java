package com.example.myHorseServer.dto.store;

import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GamerEquipment {
    private Integer equipmentId;
    private Store idItem;
    private Gamer gamerId;
}
