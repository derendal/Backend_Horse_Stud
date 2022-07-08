package com.example.myHorseServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authme")

public class Authme {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id", nullable = false, unique=true)
    private Integer id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name = "realname")
    private String realname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="ip", nullable = false)
    private String ip;

    @Column(name="lastlogin", nullable = true)
    private Integer lastLogin;

    @Column(name="x", nullable = true)
    private double x;

    @Column(name="y", nullable = true)
    private double y;

    @Column(name="z", nullable = true)
    private double z;

    @Column(name="world", nullable = false)
    private String world;

    @Column(name="regdate", nullable = true)
    private Integer regdate;

    @Column(name="regip", nullable = true)
    private String regip;

    @Column(name="yaw", nullable = true)
    private Float yaw;

    @Column(name="pitch")
    private Float pitch;

    @Column(name="email")
    private String email;

    @Column(name="isLogged")
    private boolean gamerLogged;

    @Column(name="hasSession")
    private boolean hasSession;

    @Column(name="totp", nullable = true)
    private String totp;

}
