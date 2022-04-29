package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Exceptions.UserDetailsMissingException;
import com.Group1.Sprint.Models.MatchesModel;
import com.Group1.Sprint.Models.TeamModel;
import com.Group1.Sprint.Models.TournamentModel;
import com.Group1.Sprint.Repositories.MatchesRepository;
import com.Group1.Sprint.Repositories.TeamRepository;
import com.Group1.Sprint.Repositories.TournamentRepository;
import com.Group1.Sprint.Services.IAdminServices;
import com.Group1.Sprint.Services.JWTutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    IAdminServices adminServices;


    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private JWTutil jwTutil;
  
    private String adminusername="admin";
    private String adminpassword="admin123";
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Map<String,String> userDetails)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            if(userDetails.get("username")== null)
            {
                throw new UserDetailsMissingException("Please Enter Username");
            }
            if(userDetails.get("password")== null)
            {
                throw new UserDetailsMissingException("Please Enter Password");
            }
            if(userDetails.get("username").equals(adminusername) && userDetails.get("password").equals(adminpassword))
            {
                String to =this.jwTutil.generateToken(new User(userDetails.get("username"),userDetails.get("password"),new ArrayList<>()));
                response.put("Status","Successful");
                response.put("Token",to);
                response.put("Role","ADMIN");
                return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
            }
        }
        catch(Exception e)
        {
            response.put("Status","Failed");
            response.put("Error",e.getMessage());
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);

    }


    @PostMapping("/tournament/{id}/scheduleMatches")
    public ResponseEntity<Map<String, String>> scheduleMatches(@RequestBody Map<String, String> matchDetails,@PathVariable(value = "id") int tournamentId) {
        Map<String, String> response = new HashMap<>();
        try {
            if (adminServices.scheduleMatches(matchDetails, tournamentId)) {
                response.put("Status", "Successful");
            }
        } catch (Exception e) {
            response.put("Status", "Failed");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Map<String, String>> reschedule(@RequestBody Map<String, String> newMatchDetails, @PathVariable(value = "id") int matchId)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            if (adminServices.rescheduleMatches(newMatchDetails,matchId)) {
                response.put("Status", "Successful");
            }
        }
        catch(Exception e)
        {
            response.put("Status","Failed");
            response.put("Error",e.getMessage());
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createTeam")
    public ResponseEntity<Map<String,String>> createTeam(@RequestBody Map<String,String> teamDetails)
    {
        Map<String, String> response = new HashMap<>();

        if(teamDetails.get("teamname")==null)
        {
            response.put("Status","Failed");
            response.put("Error","Enter Team Name");
            return ResponseEntity.badRequest().body(response);
        }
        if(teamDetails.get("teamcount")==null)
        {
            response.put("Status","Failed");
            response.put("Error","Enter Number of players");
            return ResponseEntity.badRequest().body(response);
        }

        TeamModel teamModel = new TeamModel(teamDetails.get("teamname"),teamDetails.get("teamcount"));
        teamRepository.save(teamModel);
        response.put("Status","Successful");
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/createTournament")
    public ResponseEntity<Map<String, String>> createTournament(@RequestBody Map<String,String> request)
    {
        Map<String, String> response = new HashMap<>();
        try {
            if (adminServices.createTournament(request)) {
                response.put("Status", "Successful");
            }
        }
        catch(Exception e)
        {
            response.put("Status","Failed");
            response.put("Error",e.getMessage());
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}/setWinner")
    public ResponseEntity<Map<String, String>> setWinner(@RequestBody Map<String, String> winner, @PathVariable(value = "id") int matchId)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            if (adminServices.setWinner(winner,matchId)) {
                response.put("Status", "Successful");
            }
        }
        catch(Exception e)
        {
            response.put("Status","Failed");
            response.put("Error",e.getMessage());
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
    @GetMapping("/getMatchDetails/{id}")
    public ResponseEntity<MatchesModel> getMatchDetails(@PathVariable(value = "id")int matchId)
    {
        return adminServices.getMatchDetails(matchId);
    }
    @GetMapping("/getMatches/{id}")
    public ResponseEntity<List<MatchesModel>> getMatches(@PathVariable(value = "id") int tournamentId)
    {
        Optional<TournamentModel> tour = tournamentRepository.findById(tournamentId);

        if(tour.isPresent()) {
            return ResponseEntity.ok().body(tour.get().getMatches());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getTeams/{id}")
    public List<Integer> getTeamTounaments(@PathVariable(value = "id") int tournamentId)
    {
        return adminServices.getTeamTounaments(tournamentId);
    }

}
