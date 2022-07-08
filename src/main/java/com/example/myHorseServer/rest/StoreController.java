package com.example.myHorseServer.rest;

import com.example.myHorseServer.dto.store.StoreCreateResponse;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Store;
import com.example.myHorseServer.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor

public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping(value = "/items")
    public ResponseEntity<Iterable<Store>> getItems(){
        System.out.println("Get events");
        return new ResponseEntity<Iterable<Store>>(storeService.findAllItems(), HttpStatus.OK);
    }
    @GetMapping(value="/itembyid")
    public ResponseEntity<Store> getEventById(@RequestBody Integer idItem){
        System.out.println("Get Item by ID");
        return new ResponseEntity<Store>(storeService.loadItemByIdItem(idItem),HttpStatus.OK);
    }
    @PutMapping(value = "/change/item" )
    public ResponseEntity<?> changeItem(@AuthenticationPrincipal Gamer gamer,@RequestBody Store store){
        System.out.println("Change event");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            storeService.changeItem(store);
            return ResponseEntity.ok().build();
        }else return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
    @DeleteMapping(value = "/delete/{iditem}")
    public ResponseEntity<StoreCreateResponse> deleteEventList(@AuthenticationPrincipal Gamer gamer, @PathVariable Integer idItem) {
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            return ResponseEntity.ok(storeService.deleteItem(idItem));
        }
        return ResponseEntity.badRequest().body(new StoreCreateResponse(null, "Forbidden"));
    }
    @CrossOrigin
    @PostMapping(value = "/new/item")
    public ResponseEntity<StoreCreateResponse> createEvent(@AuthenticationPrincipal Gamer gamer, @RequestBody Store store){
        StoreCreateResponse storeCreateResponse = storeService.createItem(store);
        System.out.println("--- New event created ---");
        if(gamer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            if (storeCreateResponse.getMessage().equals("Create Item - successful")) {
                return new ResponseEntity<>(storeCreateResponse, HttpStatus.OK);
            } else return new ResponseEntity<>(storeCreateResponse, HttpStatus.BAD_REQUEST);
        }else return (ResponseEntity<StoreCreateResponse>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }
}
