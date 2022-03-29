package com.example.auction.database.repositories;

import com.example.auction.database.entities.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotRepository extends  JpaRepository<LotEntity, String> {
    Optional<LotEntity> findOptionalById(String id);

    boolean existsById(String id);
}
