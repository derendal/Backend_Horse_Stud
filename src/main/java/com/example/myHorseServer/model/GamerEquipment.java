package com.example.myHorseServer.model;


import lombok.*;
import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gamer_equipment")


public class GamerEquipment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="equipment_id", nullable = false, unique=true)
    private Integer equipmentId;

    @ManyToOne()
    @JoinColumn(name = "id_item")
    private Store idItem;

    @OneToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamerId;
}

