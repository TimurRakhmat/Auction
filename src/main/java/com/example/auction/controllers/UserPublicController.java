package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/user")
public class UserPublicController {
    private final AuctionUserService auctionUserService;

    public UserPublicController(AuctionUserService auctionUserService){
        this.auctionUserService = auctionUserService;
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserRequest user) throws AuctionUserNotExisted {
        return auctionUserService.login(user);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String userId) throws AuctionUserNotExisted {
        return auctionUserService.getUser(userId);
    }
}
