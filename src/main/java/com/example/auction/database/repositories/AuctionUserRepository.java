package com.example.auction.database.repositories;

import com.example.auction.database.entities.AuctionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionUserRepository extends JpaRepository<AuctionUser, String> {
    Optional<AuctionUser> findOptionalByEmail(String email);
}
