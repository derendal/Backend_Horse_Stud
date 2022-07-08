package com.example.myHorseServer.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Store {
    private Integer idItem;
    private String nameItem;
    private String description;
    private Double price;
    private String imageSrc;
    private String value;
}
