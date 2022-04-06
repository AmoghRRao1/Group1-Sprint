package com.Group1.Sprint.Models;

import javax.persistence.*;

@Entity
@Table(name="Bids")
public class BidsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bidId;

    private int bidderId;

    private int matchId;

    private int teamId;

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId,int matchId,int teamId,int bidderId) {
        this.bidId = bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}

