package com.Group1.Sprint.Services;

import com.Group1.Sprint.Models.BidsModel;
import com.Group1.Sprint.Models.MatchesModel;
import com.Group1.Sprint.Models.TeamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BidderService<BidsRepository, TeamRepository, MatchesRepository, BidderRepository> implements IBidderService {
    @Autowired
    BidderRepository bidderRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Override
    public boolean bid(Map<String,Integer> bidDetails, int bidderId)
    {
        if(bidDetails.get("matchId")==null){throw new RuntimeException("Enter Match ID");}
        if(bidDetails.get("teamId")==null){throw new RuntimeException("Enter Team ID");}

        Optional<MatchesModel> match = matchesRepository.findById(bidDetails.get("matchId"));
        Optional<TeamModel> team = teamRepository.findById(bidDetails.get("teamId"));

        if(!match.isPresent()){ throw new RuntimeException("Match Not Present");}
        if(!team.isPresent()){throw new RuntimeException("Team Not Present");}

        MatchesModel m = match.get();
        TeamModel t = team.get();
        BidsModel bidsModel = new BidsModel();
        bidsModel.setBidderId(bidderId);
        bidsModel.setMatchId(m.getMatchId());
        bidsModel.setTeamId(t.getTeamId());
        bidsRepository.save(bidsModel);

        return true;
    }


}
