package com.Group1.Sprint.Models;


import javax.persistence.*;

@Entity
@Table(name = "points")
public class PointsModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int pointsId;

    @Column(name = "tournament")
    private int tournamentId;

    @Column(name ="teamid")
    private int teamId;

    @Column(name ="teampoints")
    private int points=0;

    public PointsModel()
    {

    }

    public PointsModel(int tournamentId,int teamId)
    {
        this.tournamentId = tournamentId;
        this.teamId = teamId;
    }

    public int getPointsId() {
        return pointsId;
    }

    public void setPointsId(int pointsId) {
        this.pointsId = pointsId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
