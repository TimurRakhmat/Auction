package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuctionUserService userService;

    public UserController(AuctionUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String userId) throws AuctionUserNotExisted {
        return userService.getUser(userId);
    }

    @PostMapping("/public/login")
    public UserDto login(@RequestBody UserRequest user, OurAuthToken authToken) throws AuctionUserNotExisted{
        return userService.login(user, authToken);
    }

    @PostMapping("/money")
    public UserDto money(@RequestBody UserRequest user, OurAuthToken ourAuthToken) {
        return userService.money(user);
    }
}
