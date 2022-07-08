package com.example.myHorseServer.service;

import com.example.myHorseServer.dto.horse.*;
import com.example.myHorseServer.exception.NoChangeException;
import com.example.myHorseServer.exception.NotFoundException;
import com.example.myHorseServer.model.Breed;
import com.example.myHorseServer.model.Gamer;
import com.example.myHorseServer.model.Horse;
import com.example.myHorseServer.repository.BreedRepository;
import com.example.myHorseServer.repository.GamerStudRepository;
import com.example.myHorseServer.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class HorseService {

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private GamerStudRepository gamerStudRepository;

    public HorseResponse createNewHorse(Horse horse, Gamer gamer){
        Horse creator = new Horse();
        creator.setAppearance(horse.getAppearance());
        creator.setBreed(horse.getBreed());
        creator.setFast(horse.getFast());
        creator.setGamerStud(horse.getGamerStud());
        creator.setHungry(horse.getHungry());

        if(horse.getName().isEmpty()){
            List<Horse> horseList= new ArrayList<Horse>();
            int number = 0;
            findAll().forEach(horse1 ->{
                if(horse1.getGamerStud().equals(gamerStudRepository.findByGamerId(gamer.getGamerId()))){
                    horseList.add(horse);
                }
            });
            for(int i = 0; i <= horseList.size(); i++){
                number++;
                System.out.println("Number if horse in gamer stud is: " + number);
            }
            creator.setName("horse" + number);
        } else creator.setName(horse.getName());

        creator.setThirst(horse.getThirst());
        creator.setValue(horse.getValue());
        creator = horseRepository.save(creator);

        return new HorseResponse(new Horse(
                creator.getHorseId(),
                creator.getBukkitHorseId(),
                creator.getName(),
                creator.getBreed(),
                creator.getFast(),
                creator.getHungry(),
                creator.getThirst(),
                creator.getAppearance(),
                creator.getValue(),
                creator.getGamerStud()
                ),"Create new horse");
    }

    public BreedResponse createNewBreed(Breed breed){
        Breed creator = new Breed();
        creator.setHorseBreed(breed.getHorseBreed());
        creator.setFast(breed.getFast());
        creator.setHungry(breed.getHungry());
        creator.setThirst(breed.getThirst());
        creator.setAppearance(breed.getAppearance());
        creator.setValue(breed.getValue());
        creator = breedRepository.save(creator);

        return new BreedResponse(new Breed(
                creator.getBreedId(),
                creator.getHorseBreed(),
                creator.getFast(),
                creator.getHungry(),
                creator.getThirst(),
                creator.getAppearance(),
                creator.getValue()
        ),"Create new Breed");
    }

    public Iterable<Horse> findAll(){
        return horseRepository.findAll();
    }

    public Optional<Horse> changeHorse(Horse horseChange){
        System.out.println("HOOORSEEE IDDD ____" + horseChange.getHorseId());
        Horse horse = horseRepository.findByHorseId(horseChange.getHorseId())
                   .orElseThrow(()-> new NotFoundException());

           if(!horseChange.equals(horse) || horseChange != null){
               if(!horseChange.getGamerStud().equals(horse.getGamerStud()) || horseChange.getGamerStud()!=null){
                   horse.setGamerStud(horseChange.getGamerStud());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               } else if(!horseChange.getName().equals(horse.getName()) || horseChange.getName()!= null){
                   horse.setName(horseChange.getName());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               }else if(horseChange.getBreed() != null && !horseChange.getBreed().equals(horse.getBreed()) ){
                   horse.setBreed(horseChange.getBreed());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               }else if (horseChange.getFast() != horse.getHorseId()){
                    horse.setFast(horseChange.getFast());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               }else if(horseChange.getThirst() != 0 && horseChange.getThirst() != horse.getThirst()){
                   horse.setThirst(horseChange.getThirst() + horse.getThirst());
                   System.out.println("oki?");
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               }else if(horseChange.getAppearance()!= 0 && horseChange.getAppearance()!=horse.getAppearance()){
                   horse.setAppearance(horseChange.getAppearance());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               }else if(horseChange.getValue()!=0 && horseChange.getValue()!=horse.getValue()){
                   horse.setValue(horse.getValue());
                   horseRepository.save(horse);
                   return horseRepository.findByHorseId(horse.getHorseId());
               } else throw new NoChangeException("No change in value");
           }else throw new NoChangeException("No change in horse");
    }
    public void changeBreed(Breed breedChange){
        Breed breed = breedRepository.findByBreedId(breedChange.getBreedId())
                .orElseThrow(()-> new NotFoundException());

        if(breedChange != null && !breedChange.equals(breed)){
            if(breedChange.getHorseBreed()!= null && !breedChange.getHorseBreed().equals(breed.getHorseBreed())){
                breed.setHorseBreed(breedChange.getHorseBreed());
            }else throw new NoChangeException("No change in horse breed - breed");
            if(breedChange.getFast() != 0 && breedChange.getFast() != breed.getFast() ){
                breed.setFast(breedChange.getFast());
            }else throw new NoChangeException("No change in fast - breed");
            if(breedChange.getHungry() != 0 && breedChange.getHungry() != breed.getHungry()){
                breed.setHungry(breedChange.getHungry());
            }else throw new NoChangeException("No change in hungry - breed");
            if(breedChange.getThirst() != 0 && breedChange.getThirst() != breed.getThirst()){
                breed.setThirst(breedChange.getThirst());
            }else throw new NoChangeException("No change in thirsty - breed");
            if(breedChange.getAppearance()!= 0 && breedChange.getAppearance()!=breed.getAppearance()){
                breed.setAppearance(breedChange.getAppearance());
            }else throw new NoChangeException("No change in appearance - breed");
            if(breedChange.getValue()!=0 && breedChange.getValue()!=breed.getValue()){
                breed.setValue(breed.getValue());
            } else throw new NoChangeException("No change in value - breed");
        }else throw new NoChangeException("No change in breed");
        breedRepository.save(breed);
    }
    public HorseResponse deleteHorse(Integer horseId){
        Horse horse = horseRepository.findByHorseId(horseId)
                .orElseThrow(()-> new NotFoundException());
        horseRepository.deleteById(horse.getHorseId());
        return new HorseResponse(new Horse(
                horse.getHorseId(),
                horse.getBukkitHorseId(),
                horse.getName(),
                horse.getBreed(),
                horse.getFast(),
                horse.getHungry(),
                horse.getThirst(),
                horse.getAppearance(),
                horse.getValue(),
                horse.getGamerStud()
                ),"Deleted successfull");
    }

    public BreedDeleteResponse deleteBreed(Integer breedId){
        Breed breed = breedRepository.findByBreedId(breedId)
                .orElseThrow(()-> new NotFoundException());
        breedRepository.deleteById(breed.getBreedId());

        return new BreedDeleteResponse(new Breed(
                breed.getBreedId(),
                breed.getHorseBreed(),
                breed.getFast(),
                breed.getHungry(),
                breed.getThirst(),
                breed.getAppearance(),
                breed.getValue()
        ),"Deleted successfull");
    }
}
