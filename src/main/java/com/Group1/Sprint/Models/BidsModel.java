package com.Group1.Sprint.Models;
import javax.persistence.*;

@Entity
@Table(name="Bids")
public class BidsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bidId;

    private BidderModel bidder;
    private MatchModel match;
    private TeamModel team;

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }
}
