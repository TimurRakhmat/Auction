package com.example.auction.services;

import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.LotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotService {

    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    //////////////////////////////////////////////////////////////
    //                    public
    //////////////////////////////////////////////////////////////

    public LotDto saveLot(LotRequest lotRequest) throws LotNotExistException {
        if (lotRequest.getId() != null && lotRepository.existsById(lotRequest.getId())) {
            throw new LotNotExistException();
        }
        LotEntity newLot = new LotEntity();
        newLot.setName(lotRequest.getName());
        newLot.setDescription(lotRequest.getDescription());
        newLot.setPrice(lotRequest.getPrice());
        lotRepository.save(newLot);

        return new LotDto(
                newLot.getId(),
                newLot.getName(),
                newLot.getDescription(),
                newLot.getPrice()
        );
    }

    public List<LotDto> getLots() {
        List<LotEntity> allLots = lotRepository.findAll();

        return allLots.stream()
                .map(lotEntity -> new LotDto(
                        lotEntity.getId(),
                        lotEntity.getName(),
                        lotEntity.getDescription(),
                        lotEntity.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public LotDto getLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedStudent = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedStudent.orElseThrow(LotNotExistException::new);

        return new LotDto(
                lot.getId(),
                lot.getName(),
                lot.getDescription(),
                lot.getPrice()
        );
    }

    public void updateLot(LotRequest lotRequest) throws LotNotExistException {
        if (lotRequest.getId() == null || !lotRepository.existsById(lotRequest.getId())) {
            throw new LotNotExistException();
        }

        LotEntity lot = lotRepository.getById(lotRequest.getId());
        lot.setName(lotRequest.getName());
        lot.setDescription(lotRequest.getDescription());
        lot.setPrice(lotRequest.getPrice());
        lotRepository.save(lot);
    }

    public void deleteLot(String lotId) throws LotNotExistException {
        Optional<LotEntity> existedLot = lotRepository.findOptionalById(lotId);

        LotEntity lot = existedLot.orElseThrow(LotNotExistException::new);
        lotRepository.delete(lot);
    }
}