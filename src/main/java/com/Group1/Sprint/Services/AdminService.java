package com.Group1.Sprint.Services;

import com.Group1.Sprint.Exceptions.MatchAlreadyExistsExeption;
import com.Group1.Sprint.Exceptions.TournamentAlreadyExistsException;
import com.Group1.Sprint.Exceptions.TournamentDoesNotExistException;
import com.Group1.Sprint.Models.*;
import com.Group1.Sprint.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService implements IAdminServices{
    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    BidderRepository bidderRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    PointsRepository pointsRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Override
    public boolean scheduleMatches(Map<String, String> matchDetails, int tournamentId) throws RuntimeException
    {
        Optional<MatchesModel> match = matchesRepository.findById(Integer.parseInt(matchDetails.get("id")));
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        if(match.isPresent())
        {
            throw new MatchAlreadyExistsExeption("Match With This ID Already Exists");
        }
        Optional<TournamentModel> tournament = tournamentRepository.findById(tournamentId);
        if(!tournament.isPresent())
        {
            throw new TournamentDoesNotExistException("Tournament With This ID Doesn't Exist");
        }
        MatchesModel model = new MatchesModel(Integer.parseInt(matchDetails.get("id")));
        if(matchDetails.get("matchdate") != null)
        {
            LocalDate newlocalDate = LocalDate.parse(matchDetails.get("matchdate"), dateformatter);
            model.setMatchDate(newlocalDate);
        }
        if(matchDetails.get("matchtime") != null)
        {
            LocalTime newlocaltime = LocalTime.parse(matchDetails.get("matchtime"), timeformatter);
            model.setMatchTime(newlocaltime);
        }
        if(matchDetails.get("team1") != null)
        {
            model.setTeam1(Integer.parseInt(matchDetails.get("team1")));
        }
        if(matchDetails.get("team2") != null)
        {
            model.setTeam2(Integer.parseInt(matchDetails.get("team2")));
        }

        model.setTournamentId(tournament.get().getTournamentId());
        MatchesModel savedMatch = matchesRepository.save(model);
        tournament.get().getMatches().add(savedMatch);
        tournamentRepository.save(tournament.get());
        return true;
    }
    @Override
    public boolean rescheduleMatches( Map<String, String> matchDetails, int matchId) throws RuntimeException
    {
        Optional<MatchesModel> match = matchesRepository.findById(matchId);
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

        if(!match.isPresent())
        {
            throw new RuntimeException("Match Doesnt exsits");
        }
        MatchesModel matchs = match.get();
        if(matchDetails.get("matchdate") != null)
        {
            LocalDate newlocalDate = LocalDate.parse(matchDetails.get("matchdate"), dateformatter);
            matchs.setMatchDate(newlocalDate);
        }
        if(matchDetails.get("matchtime") != null)
        {
            LocalTime newlocaltime = LocalTime.parse(matchDetails.get("matchtime"), timeformatter);
            matchs.setMatchTime(newlocaltime);
        }
        matchesRepository.save(matchs);
        return true;
    }

    @Override
    public boolean createTournament(Map<String,String> tournamentDetails) throws RuntimeException
    {
        if(tournamentDetails.get("tournamentId")==null)
        {
            throw new RuntimeException("no id given");
        }
        Optional<TournamentModel> tournament = tournamentRepository.findById(Integer.parseInt(tournamentDetails.get("tournamentId")));;
        if(tournament.isPresent())
        {
            throw new TournamentAlreadyExistsException("Tournament With This ID Already Exists");
        }
        if(tournamentDetails.get("teamIds")==null)
        {
            throw new RuntimeException("no teams given");
        }


        TournamentModel tournamentModel = new TournamentModel(Integer.parseInt(tournamentDetails.get("tournamentId")));
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (String field : tournamentDetails.get("teamIds").split(" +"))
            lst.add(Integer.parseInt(field));
        tournamentModel.setTeamIds(lst);
        for(int i : tournamentModel.getTeamIds()) {
            if(teamRepository.findById(i).isPresent()==false){
                throw new RuntimeException("Team doesnt exist");
            }
        }

        for(int i : tournamentModel.getTeamIds())
        {

            PointsModel pointsModel = new PointsModel(tournamentModel.getTournamentId(),i);
            pointsRepository.save(pointsModel);
        }
        tournamentRepository.save(tournamentModel);
        return true;
    }
    @Override
    public boolean setWinner(Map<String, String> winner, int matchId) {
        Optional<MatchesModel> match = matchesRepository.findById(matchId);
        if(!match.isPresent())
        {
            throw new RuntimeException("Match Doesnt exsits");
        }
        if(winner.get("winnerId") == null) {
            throw new RuntimeException("Please Enter Winner");
        }

        Optional<TeamModel> teamModel = teamRepository.findById(Integer.parseInt(winner.get("winnerId")));
        match.get().setWinner(teamModel.get().getTeamName());
        match.get().setWinnerId(teamModel.get().getTeamId());
        matchesRepository.save(match.get());
        //points update
        List<PointsModel> pointsModel = pointsRepository.findByTournamentId(match.get().getTournamentId());
        for(PointsModel i : pointsModel)
        {
            if(i.getTeamId()== Integer.parseInt(winner.get("winnerId")))
            {
                i.setPoints(i.getPoints()+1);
                pointsRepository.save(i);
            }
        }
        //update bidder points
        List<BidsModel> bidderModels = bidsRepository.findByMatchId(match.get().getMatchId());
        for(BidsModel i : bidderModels)
        {
            if(i.getTeamId() == Integer.parseInt(winner.get("winnerId")))
            {
                Optional<BidderModel> bidderModel = bidderRepository.findById(i.getBidderId());
                bidderModel.get().setPoints(bidderModel.get().getPoints()+1);
                bidderRepository.save(bidderModel.get());

            }
        }
        return true;

    }
    @Override
    public ResponseEntity<MatchesModel> getMatchDetails(int matchId) {

        Optional<MatchesModel> match = matchesRepository.findById(matchId);
        if(match.isPresent()) {
            return ResponseEntity.ok().body(match.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public List<Integer> getTeamTounaments(int tournamentId) {

        Optional<TournamentModel> tournament = tournamentRepository.findById(tournamentId);
        if(tournament.isPresent()) {
            return tournament.get().getTeamIds();
        } else {
            return null;
        }

    }

}
