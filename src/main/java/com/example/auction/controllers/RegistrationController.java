package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.RegistrationParamsRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.services.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody RegistrationParamsRequest params) throws AuctionUserAlreadyExistException {
        return registrationService.signup(params);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserRequest userRequest) throws AuctionUserNotExisted {
        return registrationService.login(userRequest);
    }
}
