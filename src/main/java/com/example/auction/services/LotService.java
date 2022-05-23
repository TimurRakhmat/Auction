package com.example.auction.services;

import com.example.auction.controllers.exceptions.ImageNotExistsException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.security.models.OurAuthToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LotService {
    LotDto saveLot(LotRequest lotRequest, OurAuthToken ourAuthToken) throws UnsupportedEncodingException, FileNotFoundException, IOException;

    LotDto getLot(String lotId) throws LotNotExistException;

    void deleteLot(String lotId) throws LotNotExistException;

    List<LotDto> getAllLots();

    String getLotImage(String lotImageId) throws ImageNotExistsException;

    List<LotDto> getMostPopularLots();
}
