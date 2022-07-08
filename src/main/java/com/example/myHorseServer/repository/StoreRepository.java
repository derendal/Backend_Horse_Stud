package com.example.myHorseServer.repository;
import com.example.myHorseServer.model.Store;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Integer> {

    @Cacheable
    Optional<Store> findByIdItem(Integer idItem);
}
