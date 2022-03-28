package com.example.auction.controllers;

import com.example.auction.controllers.models.LotRequest;
import com.example.auction.controllers.models.LotDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    @GetMapping("/{id}")
    public LotRequest getLot(@PathVariable("id") String lotId) {

        return null;
    }


    @PostMapping("")
    public LotDto addLot(@RequestBody LotRequest lot){

        return null;
    }

    @GetMapping("/user/{id}")
    public List<LotDto> getUserLots(@PathVariable("id") String userId){

        return null;
    }

    @GetMapping("/all")
    public List<LotDto> getAllLots(){

        return null;
    }

    @GetMapping("/user/lots")
    public List<LotDto> getUserLots(){

        return null;
    }

    @GetMapping("/top")
    public List<LotDto> getTopLots(){

        return null;
    }
}
