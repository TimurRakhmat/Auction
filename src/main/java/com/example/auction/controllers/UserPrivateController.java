package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/user")
public class UserPrivateController {

    private final AuctionUserService auctionUserService;

    public UserPrivateController(AuctionUserService auctionUserService) {
        this.auctionUserService = auctionUserService;
    }


    @PostMapping("/money")
    public UserDto money(@RequestBody UserRequest user, OurAuthToken ourAuthToken) {
        return auctionUserService.money(user, ourAuthToken);
    }

}
