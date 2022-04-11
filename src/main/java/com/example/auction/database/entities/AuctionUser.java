package com.example.auction.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.el.util.Validation;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "auction_user")
public class AuctionUser {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime addDate;

    private String name;

    private String email;
    private String password;

    private Double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Bet> ownBets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<LotEntity> ownLots;

    ///////////////////////////////////////////////////////////////////////////
    //                      equals + hash
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionUser that = (AuctionUser) o;
        return Objects.equals(id, that.id) && Objects.equals(addDate, that.addDate) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addDate, name, email, password, balance);
    }
}
