package com.Group1.Sprint.Services;

import com.Group1.Sprint.Exceptions.MatchAlreadyExistsExeption;
import com.Group1.Sprint.Exceptions.TournamentDoesNotExistException;
import com.Group1.Sprint.Models.MatchesModel;
import com.Group1.Sprint.Models.TournamentModel;
import com.Group1.Sprint.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
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


        MatchesModel savedMatch = matchesRepository.save(model);
        tournament.get().getMatches().add(savedMatch);
        tournamentRepository.save(tournament.get());
        return true;
    }
}
