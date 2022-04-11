package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetLessException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.database.entities.Bet;
import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.BetRepository;
import com.example.auction.database.repositories.LotRepository;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.BetService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BetServiceImpl implements BetService {
    private final BetRepository betRepository;
    private final ModelMapper modelMapper;
    private final LotRepository lotRepository;
    public BetServiceImpl(LotRepository lotRepository, ModelMapper modelMapper, BetRepository betRepository) {
        this.modelMapper = modelMapper;
        this.betRepository = betRepository;
        this.lotRepository = lotRepository;
    }

    @Override
    public BetDto saveBet(BetRequest betRequest, OurAuthToken ourAuthToken)throws BetLessException,
            LotNotExistException {

        Optional<LotEntity> optionalLot = lotRepository.findOptionalById(betRequest.getLotId());

        if (optionalLot.isEmpty())
            throw new LotNotExistException();

        LotEntity lot = optionalLot.get();

        if(lot.getBestBet().getAmount() >= betRequest.getAmount())
            throw new BetLessException();

        Bet newBet = new Bet();

        newBet.setAmount(betRequest.getAmount());
        newBet.setOwnLot(lot);
        newBet.setOwner(lot.getUser());

        lot.setBestBet(newBet);
        betRepository.save(newBet);
        lotRepository.save(lot);

        return modelMapper.map(newBet, BetDto.class);
    }

    @Override
    public BetDto getBet(String id) throws BetNotExistException {
        Optional<Bet> findOptionalBet = betRepository.findOptionalById(id);

        if (findOptionalBet.isEmpty())
            throw new BetNotExistException();

        Bet findBet = findOptionalBet.get();

        return modelMapper.map(findBet, BetDto.class);
    }

    @Override
    public List<BetDto> getUserBets(String userId){
        return betRepository.findBetsByOwnerId(userId).stream()
                .map(obj -> modelMapper.map(obj, BetDto.class)).collect(Collectors.toList());
    }

}
