package com.example.myHorseServer.service;

import com.example.myHorseServer.dto.store.EquipmentCreateResponse;
import com.example.myHorseServer.exception.EventNotFoundException;
import com.example.myHorseServer.model.GamerEquipment;
import com.example.myHorseServer.repository.GamerEquipmentRepository;
import com.example.myHorseServer.repository.GamerRepository;
import com.example.myHorseServer.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class GamerEquipmentService {

    @Autowired
    private GamerEquipmentRepository gamerEquipmentRepository;

    @Autowired
    private GamerRepository gamerRepository;

    @Autowired
    private StoreRepository storeRepository;

    public GamerEquipment createEqiupment(GamerEquipment gamerEquipment){

        GamerEquipment creator = new GamerEquipment();
        creator.setGamerId(gamerRepository.findByEmail(gamerEquipment.getGamerId().getEmail()).get());
        creator.setIdItem(storeRepository.findByIdItem(gamerEquipment.getIdItem().getIdItem()).get());
        gamerEquipmentRepository.save(creator);

        return new GamerEquipment(
                creator.getEquipmentId(),
                creator.getIdItem(),
                creator.getGamerId()
        );
    }
    public Iterable<GamerEquipment> findAllItems(){

        return gamerEquipmentRepository.findAll();
    }
    public GamerEquipment loadByEquipmentId(Integer equipmentId){
        return gamerEquipmentRepository.findByEquipmentId(equipmentId).orElseThrow(() -> new EventNotFoundException(format("Equipment with id - %s, not found", equipmentId)));
    }
    public GamerEquipment loadByGamerId(Integer gamerId){
        return gamerEquipmentRepository.findByGamerId(gamerId).orElseThrow(() -> new EventNotFoundException(format("Equipment with id - %s, not found", gamerId)));
    }
    public GamerEquipment loadByIdItem(Integer idItem){
        return gamerEquipmentRepository.findByIdItem(idItem).orElseThrow(() -> new EventNotFoundException(format("Equipment with id - %s, not found", idItem)));
    }

    public void changeEquipment(GamerEquipment equipment){
        GamerEquipment editor = gamerEquipmentRepository.findByEquipmentId(equipment.getEquipmentId()).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",equipment.getEquipmentId())));
        if(equipment.getIdItem()!=null && !equipment.getIdItem().equals(editor.getIdItem())){
            editor.setIdItem(equipment.getIdItem());
            gamerEquipmentRepository.save(editor);
        } if(equipment.getGamerId()!=null && equipment.getGamerId().equals(editor.getGamerId())){
            editor.setGamerId(equipment.getGamerId());
            gamerEquipmentRepository.save(editor);
        } else throw new RuntimeException("Brak zmian");
    }
    public EquipmentCreateResponse deleteEquipment(Integer equipmentId){
        GamerEquipment delete = gamerEquipmentRepository.findByEquipmentId(equipmentId).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",equipmentId)));
        gamerEquipmentRepository.deleteById(equipmentId);
        return new EquipmentCreateResponse(new GamerEquipment(
                delete.getEquipmentId(),
                delete.getIdItem(),
                delete.getGamerId()
        ),"Deleted equipment - successfull");
    }
}
