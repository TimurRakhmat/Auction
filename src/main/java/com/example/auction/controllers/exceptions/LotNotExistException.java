package com.example.auction.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Lot is not exist") // 404
public class LotNotExistException extends Exception {
}