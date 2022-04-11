package com.example.auction.services;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetLessException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.security.models.OurAuthToken;

import java.util.List;

public interface BetService {
    BetDto saveBet(BetRequest betRequest, OurAuthToken ourAuthToken) throws BetLessException, LotNotExistException;

    BetDto getBet(String id) throws BetNotExistException;
    List<BetDto> getUserBets(String id);
}
