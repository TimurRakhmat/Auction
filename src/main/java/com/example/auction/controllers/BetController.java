package com.example.auction.controllers;

import com.example.auction.controllers.models.BetRequest;
import com.example.auction.controllers.models.BetResponse;
import com.example.auction.controllers.models.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bets")
public class BetController {
    private List<BetResponse> bets;

    @PostMapping("")
    public BetRequest addBet(@RequestBody BetRequest betRequest){

        return null;
    }

    @GetMapping("")
    public List<BetResponse> getBets(@RequestBody UserRequest userRequest){

        return null;
    }
}
