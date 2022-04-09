package com.example.auction.controllers;

import com.example.auction.controllers.exceptions.LotNotExistException;
import com.example.auction.controllers.models.LotRequest;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.services.LotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lots")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }


    @GetMapping("/{id}")
    public LotDto getLot(@PathVariable("id") String lotId) throws LotNotExistException {
        return lotService.getLot(lotId);
    }


    @PostMapping("")
    public String addLot(@RequestBody LotRequest lotRequest) throws LotNotExistException {
        return lotService.saveLot(lotRequest).getId();
    }

    @GetMapping("")
    public List<LotDto> getStudents(@RequestParam(value = "q", required = false) String q) {
        List<LotDto> lots = lotService.getLots();
        if (q == null) {
            return lots;
        }
        return lots.stream()
                .filter(lot -> lot.getName().contains(q) || String.valueOf(lot.getDescription()).contains(q))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteLot(@PathVariable("id") String lotId) throws LotNotExistException {
        lotService.deleteLot(lotId);
    }


    /*
    @GetMapping("/all")
    public List<LotDto> getAllLots(){

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
    */
}
