package com.Group1.Sprint.Models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name="Teams")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teamId;

    @Column(name = "teamname")
    private String teamName;

    @Column(name = "teamcount")
    private String teamCount;

    public TeamModel() { }

    public TeamModel(String teamName,String teamCount)
    {
        this.teamName =teamName;
        this.teamCount =teamCount;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(String teamCount) {
        this.teamCount = teamCount;
    }

    public String getTeamName() {
        return teamName;
    }
}
