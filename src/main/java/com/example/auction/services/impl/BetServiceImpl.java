package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.*;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.database.entities.AuctionUser;
import com.example.auction.database.entities.Bet;
import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.database.repositories.BetRepository;
import com.example.auction.database.repositories.LotRepository;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.BetService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BetServiceImpl implements BetService {
    private final BetRepository betRepository;
    private final ModelMapper modelMapper;
    private final LotRepository lotRepository;
    private final AuctionUserRepository auctionUserRepository;

    public BetServiceImpl(AuctionUserRepository auctionUserRepository, LotRepository lotRepository,
                          ModelMapper modelMapper, BetRepository betRepository) {
        this.modelMapper = modelMapper;
        this.betRepository = betRepository;
        this.lotRepository = lotRepository;
        this.auctionUserRepository = auctionUserRepository;
    }

    @Override
    public BetDto saveBet(BetRequest betRequest, OurAuthToken ourAuthToken) throws BetStepException,
            LotNotExistException, AuctionUserNotEnoughMoneyException {

        Optional<LotEntity> optionalLot = lotRepository.findOptionalById(betRequest.getLotId());

        if (optionalLot.isEmpty())
            throw new LotNotExistException();

        LotEntity lot = optionalLot.get();

        AuctionUser user = auctionUserRepository.getById(ourAuthToken.getUserId());

        // Check enough money on betUser score
        if (user.getBalance() < betRequest.getAmount())
            throw new AuctionUserNotEnoughMoneyException();

        // Check correctable bet amount
        if (lot.getBestBet() != null
                && (betRequest.getAmount() - lot.getBestBet().getAmount()) < getLotBetStep(lot)) {
            throw new BetStepException();
        } else if (betRequest.getAmount() < (lot.getStartPrice() + getLotBetStep(lot))) {
            throw new BetStepException();
        }


        // Return old best bet owner money
        if (lot.getBestBet() != null)
            lot.getBestBet().getOwner().setBalance(lot.getBestBet().getOwner().getBalance()
                    + lot.getBestBet().getAmount());

        lot.setPopularity(lot.getPopularity() + 1);


        // Subtract new best bet owner money
        user.setBalance(user.getBalance() - betRequest.getAmount());

        Bet newBet = new Bet();

        newBet.setAmount(betRequest.getAmount());
        newBet.setOwnLot(lot);
        newBet.setOwner(user);

        lot.setBestBet(newBet);
        betRepository.save(newBet);

        return modelMapper.map(newBet, BetDto.class);
    }

    @Override
    public BetDto getBet(String id) throws BetNotExistException {
        Optional<Bet> findOptionalBet = betRepository.findOptionalById(id);

        if (findOptionalBet.isEmpty())
            throw new BetNotExistException();

        Bet findBet = findOptionalBet.get();

        BetDto mappedBetDto = modelMapper.map(findBet, BetDto.class);

        mappedBetDto.setOwnLotId(findBet.getOwnLot().getId());

        return mappedBetDto;
    }

    private Integer getLotBetStep(@NotNull LotEntity lotEntity) {
        int step = (int) Math.round(lotEntity.getStartPrice() / 100);
        return Math.max(step, 1000);
    }

}
