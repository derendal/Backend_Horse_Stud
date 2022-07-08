package com.example.myHorseServer.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gamer_stud")
@ToString
public class GamerStud {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="gamer_stud_id", nullable = false, unique=true)
    private  Integer gamerStudId;

    @OneToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamerId;

    @Column(name="stud_name", nullable = false)
    private String studName;

}
