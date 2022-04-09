package com.example.auction.services;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.models.RegistrationParamsRequest;
import com.example.auction.controllers.models.UserDto;

public interface RegistrationService {
    UserDto signup(RegistrationParamsRequest registrationParamsRequest) throws AuctionUserAlreadyExistException;
}
