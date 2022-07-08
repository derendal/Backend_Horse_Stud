package com.example.myHorseServer.rest;

import com.example.myHorseServer.dto.horse.BreedDeleteResponse;
import com.example.myHorseServer.dto.horse.BreedResponse;
import com.example.myHorseServer.dto.horse.HorseResponse;
import com.example.myHorseServer.model.Breed;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Horse;
import com.example.myHorseServer.service.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/horse")
@RequiredArgsConstructor

public class HorseController {

    @Autowired
    private HorseService horseService;

    @GetMapping(value = "/horses")
    public ResponseEntity<Iterable<Horse>> getHorses(){
        System.out.println("Get horses");
        return new ResponseEntity<Iterable<Horse>>(horseService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/changehorse" )
    public ResponseEntity<Optional<Horse>> changeHorse(@AuthenticationPrincipal Gamer gamer, @RequestBody Horse horse){
        System.out.println("Horse change");
        return new ResponseEntity<Optional<Horse>>( horseService.changeHorse(horse), HttpStatus.OK);
    }

    @PutMapping(value = "/changebreed" )
    public ResponseEntity<?> changeBreed(@AuthenticationPrincipal Gamer gamer, @RequestBody Breed breed){
        System.out.println("Breed change");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            horseService.changeBreed(breed);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "/deletehorse/{horseid}")
    public ResponseEntity<HorseResponse> deleteHorse(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer horseid) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(horseService.deleteHorse(horseid));
        }
        return ResponseEntity.badRequest().body(new HorseResponse(null, "Forbidden"));
    }

    @DeleteMapping(value = "/deletebreed/{breedid}")
    public ResponseEntity<BreedDeleteResponse> deleteBreed(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer breedid) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(horseService.deleteBreed(breedid));
        }
        return ResponseEntity.badRequest().body(new BreedDeleteResponse(null, "Forbidden"));
    }

    @CrossOrigin
    @PostMapping(value = "/newhorse")
    public ResponseEntity<HorseResponse> createNewHorse(@AuthenticationPrincipal Gamer gamer, @RequestBody Horse horse){
        HorseResponse horseResponse = horseService.createNewHorse(horse,gamer);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (horseResponse.getMessage().equals("Create new horse")) {
                return new ResponseEntity<>(horseResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(horseResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<HorseResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @CrossOrigin
    @PostMapping(value = "/newbreed")
    public ResponseEntity<BreedResponse> createNewBreed(@AuthenticationPrincipal Gamer gamer, @RequestBody Breed breed){
        BreedResponse breedResponse = horseService.createNewBreed(breed);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (breedResponse.getMessage().equals("Create new Breed")) {
                return new ResponseEntity<>(breedResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(breedResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<BreedResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
}


