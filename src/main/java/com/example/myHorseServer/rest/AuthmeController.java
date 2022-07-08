package com.example.myHorseServer.rest;


import com.example.myHorseServer.service.AuthmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/authme")
@RequiredArgsConstructor
public class AuthmeController {
    @Autowired
    AuthmeService authmeService;

}
