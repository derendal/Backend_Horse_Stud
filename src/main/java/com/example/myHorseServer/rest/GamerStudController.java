package com.example.myHorseServer.rest;

import com.example.myHorseServer.dto.gamerStud.GamerStudDeleteResponse;
import com.example.myHorseServer.dto.gamerStud.GamerStudRegisterResponse;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.GamerStud;
import com.example.myHorseServer.service.GamerStudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/stud")
@RequiredArgsConstructor

public class GamerStudController {

    @Autowired
    private GamerStudService gamerStudService;

    @GetMapping(value = "/studs")
    public ResponseEntity<Iterable<GamerStud>> getStuds(){
        System.out.println("Get gamers studs");
        return new ResponseEntity<Iterable<GamerStud>>(gamerStudService.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/changestud" )
    public ResponseEntity<?> editGamerStud(@AuthenticationPrincipal Gamer gamer, @RequestBody GamerStud gamerStud){
        System.out.println("Gamer stud change");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            gamerStudService.editGamerStud(gamerStud);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "/deletestud/{gamerStudId}")
    public ResponseEntity<GamerStudDeleteResponse> deleteGamerStud(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer gamerStudId) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(gamerStudService.deleteGamerStud(gamerStudId));
        }
        return ResponseEntity.badRequest().body(new GamerStudDeleteResponse(null, "Forbidden"));
    }

    @CrossOrigin
    @PostMapping(value = "/newstud")
    public ResponseEntity<GamerStudRegisterResponse> gamerStudNew(@AuthenticationPrincipal Gamer gamer,
                                                                  @RequestBody GamerStud gamerStud){
        GamerStudRegisterResponse gamerStudRegisterResponse = gamerStudService.gamerStudNew(gamerStud);
        System.out.println("--- New event created ---");
        if (gamerStudRegisterResponse.getMessage().equals("New gamer stud created successfull")) {
            return new ResponseEntity<>(gamerStudRegisterResponse, HttpStatus.OK);
        } else return new ResponseEntity<>(gamerStudRegisterResponse, HttpStatus.BAD_REQUEST);
    }
}
