package com.example.auction.controllers;

import com.example.auction.controllers.models.LotRequest;
import com.example.auction.controllers.models.LotResponse;
import com.example.auction.controllers.models.LotsRequest;
import com.example.auction.controllers.models.LotsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    @GetMapping("/{id}")
    public LotRequest getLot(@PathVariable("id") String userId) {

        return null;
    }


    @PostMapping("")
    public LotResponse addLot(@RequestBody LotRequest user){

        return null;
    }

    @GetMapping("/userLots")
    public LotsResponse getUserLots(@RequestBody LotsRequest lots){

        return null;
    }

    @GetMapping("/Lots")
    public LotsResponse getLots(){

        return null;
    }

}
