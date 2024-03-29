package com.example.auction.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "lot")
public class LotEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime addDate;

    private boolean sold;

    private String name;
    private Double startPrice;
    private String description;
    private Integer popularity;
    private String linkToImage;

    @ElementCollection
    private List<String> tags;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="auction_user")
    private AuctionUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bet")
    private Bet bestBet;

    ///////////////////////////////////////////////////////////////////////////
    //                      equals + hash
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotEntity that = (LotEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(addDate, that.addDate)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addDate, name);
    }
}
