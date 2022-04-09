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
    private String lotId;
    private Integer amount;
    private LocalDateTime creationDate;
}
