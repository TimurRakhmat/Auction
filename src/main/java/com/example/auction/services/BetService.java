package com.example.auction.services;

import com.example.auction.controllers.exceptions.*;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.security.models.OurAuthToken;

import java.util.List;

public interface BetService {
    BetDto saveBet(BetRequest betRequest, OurAuthToken ourAuthToken) throws BetStepException,
            LotNotExistException, AuctionUserNotEnoughMoneyException;

    BetDto getBet(String id) throws BetNotExistException;
}
