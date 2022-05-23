package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.ImageNotExistsException;
import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.LotService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("")
    public List<LotDto> getAllLots(){
        return lotService.getAllLots();
    }

    @GetMapping("/popular")
    public List<LotDto> getMostPopularLots(){
        return lotService.getMostPopularLots();
    }

    @GetMapping("/lotImage/{id}")
    public String getLotImage(@PathVariable("id") String lotImageId) throws ImageNotExistsException {
        return lotService.getLotImage(lotImageId);
    }

    @PostMapping("/private")
    public String addLot(@RequestBody LotRequest lotRequest, OurAuthToken ourAuthToken) throws IOException {
        return lotService.saveLot(lotRequest, ourAuthToken).getId();
    }

    @DeleteMapping("/private/{id}")
    public void deleteLot(@PathVariable("id") String lotId) throws LotNotExistException {
        lotService.deleteLot(lotId);
    }
}
