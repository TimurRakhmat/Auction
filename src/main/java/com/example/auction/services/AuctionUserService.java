package com.example.auction.services;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.security.models.OurAuthToken;

public interface AuctionUserService {
    UserDto getUser(String userId) throws AuctionUserNotExisted;
    UserDto login(UserRequest user) throws AuctionUserNotExisted;
    UserDto money(UserRequest user, OurAuthToken ourAuthToken);
}
