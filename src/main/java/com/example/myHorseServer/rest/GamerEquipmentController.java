package com.example.myHorseServer.rest;

import com.example.myHorseServer.dto.store.EquipmentCreateResponse;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.GamerEquipment;
import com.example.myHorseServer.service.GamerEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor

public class GamerEquipmentController {
    @Autowired
    private GamerEquipmentService gamerEquipmentService;

    @GetMapping(value = "/equipment")
    public ResponseEntity<Iterable<GamerEquipment>> getAllEquipment(){
        System.out.println("Get all equipment");
        return new ResponseEntity<Iterable<GamerEquipment>>(gamerEquipmentService.findAllItems(), HttpStatus.OK);
    }
    @GetMapping(value="/byeqipmentid")
    public ResponseEntity<GamerEquipment> getByEquipmentId(@RequestBody Integer equipmentId){
        System.out.println("Get Equipment by equpiment ID");
        return new ResponseEntity<GamerEquipment>(gamerEquipmentService.loadByEquipmentId(equipmentId),HttpStatus.OK);
    }
    @GetMapping(value="/byiditem")
    public ResponseEntity<GamerEquipment> getByidItem(@RequestBody Integer idItem){
        System.out.println("Get Equipment by item ID");
        return new ResponseEntity<GamerEquipment>(gamerEquipmentService.loadByIdItem(idItem),HttpStatus.OK);
    }
    @GetMapping(value="/bygamerid")
    public ResponseEntity<GamerEquipment> getByGamerId(@RequestBody Integer gamerId){
        System.out.println("Get Equipment by gamer ID");
        return new ResponseEntity<GamerEquipment>(gamerEquipmentService.loadByGamerId(gamerId),HttpStatus.OK);
    }

    @GetMapping(value = "/get/eqipmentlist")
    public ResponseEntity<Iterable<GamerEquipment>> getEquipmentList(@AuthenticationPrincipal Gamer gamer){
        System.out.println("Get event list by ID");
        return new ResponseEntity<Iterable<GamerEquipment>>((Iterable<GamerEquipment>) gamerEquipmentService.loadByGamerId(gamer.getGamerId()), HttpStatus.OK);
    }

    @PutMapping(value = "/change/equipment" )
    public ResponseEntity<?> changeEquipment(@AuthenticationPrincipal Gamer gamer,@RequestBody GamerEquipment gamerEquipment){
        System.out.println("Change event");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            gamerEquipmentService.changeEquipment(gamerEquipment);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
    @DeleteMapping(value = "/delete/{equipmentid}")
    public ResponseEntity<EquipmentCreateResponse> deleteEventList(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer equipmentid) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(gamerEquipmentService.deleteEquipment(equipmentid));
        }
        return ResponseEntity.badRequest().body(new EquipmentCreateResponse(null, "Forbidden"));
    }
    @CrossOrigin
    @PostMapping(value = "/new/item")
    public ResponseEntity createEvent(@AuthenticationPrincipal Gamer gamer, @RequestBody GamerEquipment gamerEquipment){

        GamerEquipment gamerEquipment1 = gamerEquipmentService.createEqiupment(gamerEquipment);
        System.out.println(gamerEquipment.getGamerId().getGamerId());
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(gamerEquipment1, HttpStatus.OK);
        }else return (ResponseEntity<GamerEquipment>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
}
