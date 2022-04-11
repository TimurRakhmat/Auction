package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.BetExistException;
import com.example.auction.controllers.exceptions.BetLessException;
import com.example.auction.controllers.exceptions.BetNotExistException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.BetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/private/bets")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("")
    public BetDto saveBet(@RequestBody BetRequest betRequest, OurAuthToken ourAuthToken) throws BetLessException,
            LotNotExistException {
        return betService.saveBet(betRequest, ourAuthToken);
    }

    @GetMapping("/{id}")
    public BetDto getBet(@PathVariable("id") String betId) throws BetNotExistException {
        return betService.getBet(betId);
    }

    @GetMapping("")
    public List<BetDto> getUserBets(OurAuthToken ourAuthToken) {
        return betService.getUserBets(ourAuthToken.getUserId());
    }
}
