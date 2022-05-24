package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.ImageNotExistsException;
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

import java.io.*;
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
    public LotDto saveLot(LotRequest lotRequest, OurAuthToken ourAuthToken) throws IOException {
        LotEntity newLot = mapper.map(lotRequest, LotEntity.class);
        newLot.setUser(auctionUserRepository.getById(ourAuthToken.getUserId()));

        newLot.setLinkToImage("/api/lots/lotImage/" + newLot.getId() + ".jpg");
        var img = Base64.getDecoder().decode(lotRequest.getImageBase64().getBytes("UTF-8"));
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(newLot.getId() + ".jpg")));
        stream.write(img);
        stream.close();

        // Set fields
        newLot.setPopularity(0);
        newLot.setSold(false);

        lotRepository.save(newLot);
        return mapper.map(newLot, LotDto.class);
    }

    @Override
    public LotDto getLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedStudent = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedStudent.orElseThrow(LotNotExistException::new);

        var mappedDto = mapper.map(lot, LotDto.class);
        mappedDto.setBestBet(mapper.map(lot.getBestBet(), BetDto.class));
        return mappedDto;
    }

    @Override
    public void deleteLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedLot = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedLot.orElseThrow(LotNotExistException::new);
        lotRepository.delete(lot);
    }

    @Override
    public List<LotDto> getAllLots() {
        return lotRepository.findAllBySoldIsFalse().stream()
                .map(lotEntity -> mapper.map(lotEntity, LotDto.class)).collect(Collectors.toList());
    }


    @Override
    public String getLotImage(String lotImageId) throws ImageNotExistsException {
        byte[] img;
        try {
            BufferedInputStream stream =
                    new BufferedInputStream(new FileInputStream(new File(lotImageId + ".jpg")));
            img = stream.readAllBytes();
            stream.close();
        } catch (IOException ex) {
            throw new ImageNotExistsException();
        }

        return Base64.getEncoder().encodeToString(img);
    }

    @Override
    public List<LotDto> getMostPopularLots() {
        List<LotEntity> findLotEntities = lotRepository.findAllBySoldIsFalseOrderByPopularityDesc();
        findLotEntities.subList(0, Math.min(5, findLotEntities.size()));
        return findLotEntities.stream()
                .map(lotEntity -> mapper.map(lotEntity, LotDto.class)).collect(Collectors.toList());
    }
}