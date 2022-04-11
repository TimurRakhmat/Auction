package com.example.auction.database.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name="auction_user")
    private AuctionUser owner;

    @OneToOne
    @JoinColumn(name="lot")
    private LotEntity ownLot;

    private Integer amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id)
                && Objects.equals(createDate, bet.createDate)
                && Objects.equals(owner, bet.owner)
                && Objects.equals(ownLot, bet.ownLot)
                && Objects.equals(amount, bet.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, owner, ownLot, amount);
    }
}
