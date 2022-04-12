package com.example.auction.services;

import com.example.auction.controllers.exceptions.LotAlreadyExistException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.security.models.OurAuthToken;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LotService {
    LotDto saveLot(LotRequest lotRequest, OurAuthToken ourAuthToken) throws UnsupportedEncodingException;

    LotDto getLot(String lotId) throws LotNotExistException;

    void deleteLot(String lotId) throws LotNotExistException;
}
