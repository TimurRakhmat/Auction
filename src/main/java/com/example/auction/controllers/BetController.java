package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.*;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.BetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bets")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("/private")
    public BetDto saveBet(@RequestBody BetRequest betRequest, OurAuthToken ourAuthToken) throws BetStepException,
            LotNotExistException, AuctionUserNotEnoughMoneyException, LotIsSoldException {
        return betService.saveBet(betRequest, ourAuthToken);
    }

    @GetMapping("/{id}")
    public BetDto getBet(@PathVariable("id") String betId) throws BetNotExistException {
        return betService.getBet(betId);
    }
}
