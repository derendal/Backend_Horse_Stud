package com.example.myHorseServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "plot")

public class Plot {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="plot_id", nullable = false, unique=true)
    private Integer plotId;

    @Column(name = "gamer_id")
    private Gamer gamer;

    @Column(name="x", nullable = false)
    private double x;

    @Column(name="y", nullable = false)
    private double y;

    @Column(name="z", nullable = false)
    private double z;

}
