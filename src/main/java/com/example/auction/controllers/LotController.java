package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.LotService;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/lots")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }


    @GetMapping("/{id}")
    public LotDto getLot(@PathVariable("id") String lotId) throws LotNotExistException{
        return lotService.getLot(lotId);
    }

    @PostMapping("/private/")
    public String addLot(@RequestBody LotRequest lotRequest, OurAuthToken ourAuthToken) throws UnsupportedEncodingException {
        return lotService.saveLot(lotRequest, ourAuthToken).getId();
    }

    @DeleteMapping("/private/{id}")
    public void deleteLot(@PathVariable("id") String lotId) throws LotNotExistException {
        lotService.deleteLot(lotId);
    }
}
