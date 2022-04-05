package com.example.group.sprint.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "TournmentDetails")
public class matchmodel {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Column(name = "team1")
        private TeamModel team1;

        @Column(name = "team2")
        private TeamModel team2;

        @Column(name = "matchdate")
        private LocalDate matchdate;

        @Column(name ="matchtime")
        private LocalTime matchtime;

        @Column(name ="Stadium")
        private String Stadium;

        @Column(name ="winner")
        private TeamModel winner;

        @Column(name = "status")
        private String status;


        public TeamModel getTeam1() {
            return team1;
        }

        public void setTeam1(TeamModel team1) {
            this.team1 = team1;
        }

        public TeamModel getTeam2() {
            return team2;
        }

        public void setTeam2(TeamModel team2) {
            this.team2 = team2;
        }

        public LocalDate getMatchdate() {
            return matchdate;
        }

        public void setMatchdate(LocalDate matchdate) {
            this.matchdate = matchdate;
        }

        public LocalTime getMatchtime() {
            return matchtime;
        }

        public void setMatchtime(LocalTime matchtime) {
            this.matchtime = matchtime;
        }

        public String getStadium() {
            return Stadium;
        }

        public void setStadium(String stadium) {
            Stadium = stadium;
        }

        public TeamModel getWinner() {
            return winner;
        }

        public void setWinner(TeamModel winner) {
            this.winner = winner;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


