package com.example.myHorseServer.service;

import com.example.myHorseServer.dto.store.StoreCreateResponse;
import com.example.myHorseServer.exception.EventNotFoundException;
import com.example.myHorseServer.model.Store;
import com.example.myHorseServer.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service

public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public StoreCreateResponse createItem(Store store){
        Store creator = new Store();
        creator.setNameItem(store.getNameItem());
        creator.setDescription(store.getDescription());
        creator.setPrice(store.getPrice());
        creator.setImgSrc(store.getImgSrc());
        creator.setValue(store.getValue());
        creator = storeRepository.save(creator);

        System.out.println();

        return new StoreCreateResponse(new Store(
                creator.getIdItem(),
                creator.getNameItem(),
                creator.getDescription(),
                creator.getPrice(),
                creator.getImgSrc(),
                creator.getValue()
        ),"Create Item - successful");
    }

    public Iterable<Store> findAllItems(){
        return storeRepository.findAll();
    }

    public Store loadItemByIdItem(Integer idItem){
        return storeRepository.findByIdItem(idItem).orElseThrow(() -> new EventNotFoundException(format("Item with id - %s, not found", idItem)));
    }

    public void changeItem(Store store){
        Store editor = storeRepository.findByIdItem(store.getIdItem()).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",store.getNameItem())));
        if(store.getNameItem()!=null && !store.getNameItem().equals(editor.getNameItem())){
            editor.setNameItem(store.getNameItem());
            storeRepository.save(editor);
        } if(store.getDescription()!=null && store.getDescription().equals(editor.getDescription())){
            editor.setDescription(store.getDescription());
            storeRepository.save(editor);
        }if (store.getPrice() !=null && store.getPrice().equals(editor.getPrice())){
            editor.setPrice(store.getPrice());
            storeRepository.save(editor);
        }if (store.getImgSrc()!=null && store.getImgSrc().equals(editor.getImgSrc())) {
            editor.setImgSrc(store.getImgSrc());
            storeRepository.save(editor);
        }if (store.getValue()!=null && store.getValue().equals(editor.getValue())){
            editor.setValue(store.getValue());
            storeRepository.save(editor);
        } else throw new RuntimeException("Brak zmian");
    }

    public StoreCreateResponse deleteItem(Integer idItem){
        Store delete = storeRepository.findByIdItem(idItem).orElseThrow(()-> new EventNotFoundException(format("Event result - %s, not found",idItem)));
        storeRepository.deleteById(idItem);
        return new StoreCreateResponse(new Store(
                delete.getIdItem(),
                delete.getNameItem(),
                delete.getDescription(),
                delete.getPrice(),
                delete.getImgSrc(),
                delete.getValue()
        ),"Deleted item - successfull");
    }
}
