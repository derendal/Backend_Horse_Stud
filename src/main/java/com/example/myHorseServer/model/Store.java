package com.example.myHorseServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store")

public class Store {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_item", nullable = false, unique=true)
    private Integer idItem;

    @Column(name = "name_item", nullable = false, unique = false)
    private String nameItem;

    @Column(name = "description", nullable = false, unique = false)
    private String description;

    @Column(name = "price", nullable = false, unique = false)
    private Double price;

    @Column(name = "img_src", nullable = false, unique = false)
    private String imgSrc;

    @Column(name = "value", nullable = false, unique = false)
    private String value;

}
