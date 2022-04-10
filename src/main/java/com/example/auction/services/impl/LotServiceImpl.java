package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.LotRepository;
import com.example.auction.services.LotService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final ModelMapper mapper;

    public LotServiceImpl(LotRepository lotRepository, ModelMapper mapper) {
        this.lotRepository = lotRepository;
        this.mapper = mapper;
    }

    //////////////////////////////////////////////////////////////
    //                    public
    //////////////////////////////////////////////////////////////

    @Override
    public LotDto saveLot(LotRequest lotRequest) throws LotNotExistException {
        if (lotRequest.getId() != null && lotRepository.existsById(lotRequest.getId())) {
            throw new LotNotExistException();
        }
        LotEntity newLot = mapper.map(lotRequest, LotEntity.class);
        lotRepository.save(newLot);

        return mapper.map(newLot, LotDto.class);
    }

    @Override
    public List<LotDto> getLots() {
        List<LotEntity> allLots = lotRepository.findAll();

        return allLots.stream()
                .map(LotEntity -> mapper.map(LotEntity, LotDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LotDto getLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedStudent = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedStudent.orElseThrow(LotNotExistException::new);

        return mapper.map(lot, LotDto.class);
    }

    @Override
    public void updateLot(LotRequest lotRequest) throws LotNotExistException {
        if (lotRequest.getId() == null || !lotRepository.existsById(lotRequest.getId())) {
            throw new LotNotExistException();
        }

        LotEntity lot = lotRepository.getById(lotRequest.getId());
        lot.setName(lotRequest.getName());
        lot.setDescription(lotRequest.getDescription());
        lot.setPrice(lotRequest.getPrice());
        lot.setTags(lotRequest.getTags());
        lotRepository.save(lot);
    }

    @Override
    public void deleteLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedLot = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedLot.orElseThrow(LotNotExistException::new);
        lotRepository.delete(lot);
    }
}