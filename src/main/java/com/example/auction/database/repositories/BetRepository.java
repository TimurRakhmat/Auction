package com.example.auction.database.repositories;

import com.example.auction.database.entities.Bet;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, String>{
    Optional<Bet> findOptionalById(String id);
    List<Bet> findBetsByOwnerId(String id);
    boolean existsById(@NonNull String id);
}
