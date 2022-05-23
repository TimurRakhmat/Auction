package com.example.auction.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetDto {
    private String id;
    private Double amount;
    private LocalDateTime createDate;
}
