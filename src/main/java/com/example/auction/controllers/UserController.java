package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuctionUserService auctionUserService;

    public UserController(AuctionUserService auctionUserService) {
        this.auctionUserService = auctionUserService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String userId) throws AuctionUserNotExisted {
        return auctionUserService.getUser(userId);
    }

    @GetMapping("/getLots/{id}")
    public List<LotDto> getUserLots(@PathVariable("id") String userId) throws AuctionUserNotExisted{
        return auctionUserService.getUserLots(userId);
    }
}
