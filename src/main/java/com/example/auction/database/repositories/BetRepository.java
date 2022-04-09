package com.example.auction.database.repositories;

import com.example.auction.database.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, String>{
    Optional<Bet> findOptionalById(String id);

    boolean existsById(String id);
}
