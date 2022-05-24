package com.example.auction.configurations;

import com.example.auction.database.entities.LotEntity;
import com.example.auction.database.repositories.LotRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private final List<LotEntity> prepareToSoldLots;
    private final LotRepository lotRepository;
    private final int frequency;

    SchedulerConfig(LotRepository lotRepository) {
        prepareToSoldLots = new LinkedList<>();
        this.lotRepository = lotRepository;
        this.frequency = 60000;
    }

    @Scheduled(fixedRate = 300000)
    void checkLotsToSoldWithin5Minutes() {
        List<LotEntity> notSoldLots = lotRepository.findAllBySoldIsFalse();

        prepareToSoldLots.addAll(notSoldLots.stream().filter(lotEntity ->
                lotEntity.getBestBet() != null &&
                        LocalDateTime.now().plus(5, ChronoUnit.MINUTES)
                    .compareTo(lotEntity.getBestBet().getCreateDate()) < 1
        ).toList());
    }

    @Scheduled(fixedRate = 60000)
    void checkLotsToSold() {
        prepareToSoldLots.stream().filter(lotEntity ->
                LocalDateTime.now().compareTo(lotEntity.getBestBet().getCreateDate()) > -1)
                .peek(lotEntity -> {
                    lotEntity.setSold(true);
                    prepareToSoldLots.remove(lotEntity);
                    lotRepository.save(lotEntity);
                });

    }

}
