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
@Table(name = "horse")


public class Horse {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="horse_id", nullable = false, unique=true)
    private Integer horseId;


    @Column(name = "bukkit_horse_id")
    private String bukkitHorseId;

    @Column(name="name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @Column(name="fast", nullable = false)
    private double fast;

    @Column(name="hungry", nullable = false)
    private double hungry;

    @Column(name="thirst", nullable = false)
    private double thirst;

    @Column(name="appearance", nullable = false)
    private double appearance;

    @Column(name="value", nullable = false)
    private double value;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gamer_stud_id")
    private GamerStud gamerStud;
}

