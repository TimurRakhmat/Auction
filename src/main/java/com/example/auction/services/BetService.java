package com.example.auction.services;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;

public interface BetService {
    BetDto saveBet(BetRequest betRequest) throws BetExistException;

    BetDto getBet(String id) throws BetNotExistException;
}
