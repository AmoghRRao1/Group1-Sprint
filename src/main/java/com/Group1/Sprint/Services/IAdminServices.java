package com.Group1.Sprint.Services;


import com.Group1.Sprint.Models.MatchesModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IAdminServices {

    public boolean scheduleMatches(Map<String, String> matchDetails,int tournamentId);
    public boolean rescheduleMatches( Map<String, String> matchDetails, int matchId);
    public boolean setWinner(Map<String, String> winner, int matchId);
    public boolean createTournament(Map<String,String> tournamentDetails);

}
