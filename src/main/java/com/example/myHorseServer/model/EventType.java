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
@Table(name = "event_type")

public class EventType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="event_type_id", nullable = false, unique=true)
    private Integer eventTypeId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="points_scored", nullable = false)
    private Integer pointsScored;

}
