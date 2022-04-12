package com.example.auction.services;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.RegistrationParamsRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;

public interface RegistrationService {
    UserDto signup(RegistrationParamsRequest registrationParamsRequest) throws AuctionUserAlreadyExistException;
    UserDto login(UserRequest userRequest) throws AuctionUserNotExisted;
}
