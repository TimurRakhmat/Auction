package com.example.auction.services;

import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.security.models.OurAuthToken;

import java.util.List;

public interface AuctionUserService {
    UserDto getUser(OurAuthToken ourAuthToken);
    UserDto getUser(String userId) throws AuctionUserNotExisted;
    UserDto money(UserRequest user, OurAuthToken ourAuthToken);
    List<LotDto> getUserLots(String userId) throws AuctionUserNotExisted;
    List<LotDto> getCurrentUserLots(OurAuthToken ourAuthToken);
    List<BetDto> getCurrentUserBets(OurAuthToken ourAuthToken);
}
