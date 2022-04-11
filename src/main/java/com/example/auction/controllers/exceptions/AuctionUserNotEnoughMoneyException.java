package com.example.auction.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Not enough money on user score") // 404
public class AuctionUserNotEnoughMoneyException extends Exception{
}