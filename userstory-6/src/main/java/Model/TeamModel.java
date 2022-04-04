package Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="teams")

public class TeamModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="teamid")
    private int teamid;
    @Column(name="players")
    private List<String>players;

    public TeamModel() {super();}

    public TeamModel(int teamid,List<String>players){
        
        this.teamid=teamid;
        this.players=players;
    }
    public int getTeamId(){return teamid;}
    public void setTeamId(int teamid){this.teamid=teamid;}
    public List<String> getPlayers(){return players;}
    public void setPlayers(List<String>players){this.players=players;}

}
