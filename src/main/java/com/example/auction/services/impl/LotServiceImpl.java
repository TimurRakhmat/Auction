package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.LotAlreadyExistException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.database.repositories.LotRepository;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.LotService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFormat;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final ModelMapper mapper;
    private final AuctionUserRepository auctionUserRepository;

    public LotServiceImpl(AuctionUserRepository auctionUserRepository,
                          LotRepository lotRepository, ModelMapper mapper) {
        this.lotRepository = lotRepository;
        this.mapper = mapper;
        this.auctionUserRepository = auctionUserRepository;
    }

    //////////////////////////////////////////////////////////////
    //                    public
    //////////////////////////////////////////////////////////////

    @Override
    public LotDto saveLot(LotRequest lotRequest, OurAuthToken ourAuthToken) throws UnsupportedEncodingException {
        LotEntity newLot = mapper.map(lotRequest, LotEntity.class);

        var img = Base64.getDecoder().decode(lotRequest.getImageBase64().getBytes("UTF-8"));

        newLot.setImage(img);
        newLot.setUser(auctionUserRepository.getById(ourAuthToken.getUserId()));
        lotRepository.save(newLot);

        return mapper.map(newLot, LotDto.class);
    }

    @Override
    public LotDto getLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedStudent = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedStudent.orElseThrow(LotNotExistException::new);

        var mappedDto = mapper.map(lot, LotDto.class);
        mappedDto.setImage(Base64.getEncoder().encodeToString(lot.getImage()));
        mappedDto.setBestBet(mapper.map(lot.getBestBet(), BetDto.class));
        return mappedDto;
    }

    @Override
    public void deleteLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedLot = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedLot.orElseThrow(LotNotExistException::new);
        lotRepository.delete(lot);
    }
}