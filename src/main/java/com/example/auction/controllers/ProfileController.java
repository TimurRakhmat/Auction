package com.example.auction.controllers;

import com.example.auction.controllers.models.BetDto;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final AuctionUserService auctionUserService;

    public ProfileController(AuctionUserService auctionUserService) {
        this.auctionUserService = auctionUserService;
    }

    @GetMapping("")
    public UserDto getUser(OurAuthToken ourAuthToken) {
        return auctionUserService.getUser(ourAuthToken);
    }

    @PostMapping("/money")
    public UserDto money(@RequestBody UserRequest user, OurAuthToken ourAuthToken) {
        return auctionUserService.money(user, ourAuthToken);
    }

    @GetMapping("/lots")
    public List<LotDto> getCurrentUserLots(OurAuthToken ourAuthToken){
        return auctionUserService.getCurrentUserLots(ourAuthToken);
    }

    @GetMapping("/bets")
    public List<BetDto> getCurrentUserBets(OurAuthToken ourAuthToken){
        return auctionUserService.getCurrentUserBets(ourAuthToken);
    }
}
