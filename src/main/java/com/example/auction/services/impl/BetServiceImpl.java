package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.database.entities.BetEntity;
import com.example.auction.database.repositories.BetRepository;
import com.example.auction.services.BetService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BetServiceImpl implements BetService {
    private final BetRepository betRepository;

    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public BetDto saveBet(BetRequest betRequest) throws BetExistException {

        BetEntity newBet = new BetEntity();
        newBet.setAmount(betRequest.getAmount());

        newBet.setLotId(betRequest.getLotId());
        newBet.setOwnerId("-1"); // !!!! need cur user !!!

        betRepository.save(newBet);

        return new BetDto(newBet.getId(), newBet.getLotId(), newBet.getAmount(), newBet.getCreateDate());
    }

    @Override
    public BetDto getBet(String id) throws BetNotExistException {
        Optional<BetEntity> findOptionalBet = betRepository.findOptionalById(id);

        if (findOptionalBet.isEmpty())
            throw new BetNotExistException();

        BetEntity findBet = findOptionalBet.get();

        return new BetDto(findBet.getId(), findBet.getLotId(), findBet.getAmount(), findBet.getCreateDate());
    }
}
