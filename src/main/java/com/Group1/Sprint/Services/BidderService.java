package com.Group1.Sprint.Services;

import com.Group1.Sprint.Exceptions.BidderExistsException;
import com.Group1.Sprint.Exceptions.UserDetailsMissingException;
import com.Group1.Sprint.Models.BidderModel;
import com.Group1.Sprint.Models.BidsModel;
import com.Group1.Sprint.Models.MatchesModel;
import com.Group1.Sprint.Models.TeamModel;
import com.Group1.Sprint.Repositories.BidderRepository;
import com.Group1.Sprint.Repositories.BidsRepository;
import com.Group1.Sprint.Repositories.MatchesRepository;
import com.Group1.Sprint.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BidderService implements IBidderService {
    @Autowired
    BidderRepository bidderRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Override
    public boolean login(Map<String,String> loginDetails) throws RuntimeException
    {
        if(!loginDetails.containsKey("email"))
        {
            throw new EmailMissingException("Please Enter Email");
        }
        if(!loginDetails.containsKey("password"))
        {
            throw new EmailMissingException("Please Enter Password");
        }
        Optional<BidderModel> bidder = bidderRepository.findByEmail(loginDetails.get("email"));
        if(bidder.isPresent())
        {
            if(bidder.get().getPassword().equals(loginDetails.get("password")))
            {

                return true;
            }
            else {
                throw new RuntimeException("Incorrect Details");
            }
        }
        else
        {
            throw new BidderNotRegisteredException("Please Register");
        }

    }

    @Override
    public boolean register(BidderModel obj) throws RuntimeException
    {
        if(obj.getName()==null)
        {
            throw new UserDetailsMissingException("Please Enter Name");
        }
        if(obj.getEmail()==null)
        {
            throw new UserDetailsMissingException("Please Enter Email");
        }
        Optional<BidderModel> bidder =bidderRepository.findByEmail(obj.getEmail());
        if(bidder.isPresent()){
            throw new BidderExistsException("Account already exists with this email");
        }
        if(obj.getPassword()==null)
        {
            throw new UserDetailsMissingException("Please Enter Password");
        }
        bidderRepository.save(obj);
        return true;
    }

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
