package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.database.entities.Bet;
import com.example.auction.database.repositories.BetRepository;
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
    public BetServiceImpl(ModelMapper modelMapper, BetRepository betRepository) {
        this.modelMapper = modelMapper;
        this.betRepository = betRepository;
    }

    @Override
    public BetDto saveBet(BetRequest betRequest, OurAuthToken ourAuthToken) {

        Bet newBet = new Bet();

        newBet.setAmount(betRequest.getAmount());

        newBet.setLotId(betRequest.getLotId());
        newBet.setOwnerId(ourAuthToken.getUserId());
        betRepository.save(newBet);

        return new BetDto(newBet.getId(), newBet.getLotId(), newBet.getAmount(), newBet.getCreateDate());
    }

    @Override
    public BetDto getBet(String id) throws BetNotExistException {
        Optional<Bet> findOptionalBet = betRepository.findOptionalById(id);

        if (findOptionalBet.isEmpty())
            throw new BetNotExistException();

        Bet findBet = findOptionalBet.get();

        return new BetDto(findBet.getId(), findBet.getLotId(), findBet.getAmount(), findBet.getCreateDate());
    }

    @Override
    public List<BetDto> getUserBets(OurAuthToken ourAuthToken){
        return betRepository.findBetsByOwnerId(ourAuthToken.getUserId()).stream()
                .map(obj -> modelMapper.map(obj, BetDto.class)).collect(Collectors.toList());
    }

}
