package com.example.auction.database.repositories;

import com.example.auction.database.entities.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.auction.database.entities.BetEntity;


import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<BetEntity, String>{
    Optional<BetEntity> findOptionalById(String id);

    boolean existsById(String id);
}
