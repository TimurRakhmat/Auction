package com.example.auction.services;

import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.security.models.OurAuthToken;

import java.util.List;

public interface LotService {
    LotDto saveLot(LotRequest lotRequest, OurAuthToken ourAuthToken) throws LotNotExistException;

    List<LotDto> getLots();

    LotDto getLot(String lotId) throws LotNotExistException;

    void updateLot(LotRequest lotRequest) throws LotNotExistException;

    void deleteLot(String lotId) throws LotNotExistException;
}
