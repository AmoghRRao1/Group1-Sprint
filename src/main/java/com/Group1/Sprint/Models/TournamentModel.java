package com.Group1.Sprint.Models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Tournaments")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class TournamentModel {

    @Id
    private int tournamentId;

    @Type(type = "list-array")
    @Column(name="teams",columnDefinition = "integer[]")
    private List<Integer> teamIds;

    /*@Column(name="number_of_qualifers")
    private int numberOfQualifers;

    @Column(name="number_of_matches_completed")
    private int numberOfMatchesCompleted;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;*/

    public TournamentModel(int tournamentId)
    {
        this.tournamentId =tournamentId;
    }

    public TournamentModel()
    {

    }
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournamentId",referencedColumnName = "tournamentId")
    private List<MatchesModel> matches;

    public List<MatchesModel> getMatches() {
        return matches;
    }
    public void setMatches(List<MatchesModel> matches) {
        this.matches =matches;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public List<Integer> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(ArrayList<Integer> teamIds) {
        this.teamIds = teamIds;
    }
}
