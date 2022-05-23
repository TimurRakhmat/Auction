package com.example.auction.controllers.models;

import com.example.auction.database.entities.Bet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDto {
    private String id;
    private String name;
    private Double startPrice;
    private String description;
    private List<String> tags;
    private BetDto bestBet;
    private String image;
}
