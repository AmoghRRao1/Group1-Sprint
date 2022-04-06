package com.Group1.Sprint.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="matches")
public class MatchesModel {

    @Id
    private int matchId;

    @Column(name="team1")
    private int team1;

    @Column(name="team2")
    private int team2;


    @Column(name="matchdate", columnDefinition = "DATE")
    private LocalDate matchDate;

    @Column(name="matchtime", columnDefinition = "TIME")
    private LocalTime matchTime;

    @ManyToOne
    @JoinColumn(name = "tournamentId")
    private TournamentModel tournament;

    @Column(name="winner")
    private String winner="TBA";

    @Column(name="winnerId")
    private int winnerId=-1;


    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public MatchesModel(int matchId)
    {
        this.matchId = matchId;
    }
    public MatchesModel() {  }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public LocalTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalTime matchTime) {
        this.matchTime = matchTime;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public TournamentModel getTournament() {
        return tournament;
    }

    public int getTeam1() {
        return team1;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }

    public int getTeam2() {
        return team2;
    }

    public void setTeam2(int team2) {
        this.team2 = team2;
    }

    public int getWinnerId() {
        return winnerId;
    }
}
