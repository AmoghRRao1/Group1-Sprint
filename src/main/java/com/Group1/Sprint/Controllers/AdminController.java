package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Exceptions.UserDetailsMissingException;

import com.Group1.Sprint.Models.TeamModel;
import com.Group1.Sprint.Repositories.MatchesRepository;
import com.Group1.Sprint.Repositories.TeamRepository;
import com.Group1.Sprint.Repositories.TournamentRepository;
import com.Group1.Sprint.Services.IAdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


public class AdminController {
    @Autowired
    IAdminServices adminServices;


    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    TeamRepository teamRepository;
  
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
                response.put("Status","Successful");
                response.put("SessionID","1234");
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
    public ResponseEntity<Map<String, String>> scheduleMatches(@RequestBody Map<String, String> matchDetails,@PathVariable(value = "id") int tournamentId)
    {
        Map<String, String> response = new HashMap<>();
        try {
            if (adminServices.scheduleMatches(matchDetails,tournamentId)) {
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

    @PostMapping("/createTeam")
    public ResponseEntity<String> createTeam(Map<String,String> teamDetails)
    {
        Map<String, String> response = new HashMap<>();
        if(teamDetails.get("teamname")==null)
        {
            response.put("Status","Failed");
            response.put("Error","Enter Team Name");
        }
        if(teamDetails.get("teamcount")==null)
        {
            response.put("Status","Failed");
            response.put("Error","Enter Number of players");
        }
        TeamModel teamModel =new TeamModel(teamDetails.get("teamname"),teamDetails.get("teamcount"));
        teamRepository.save(teamModel);
        return ResponseEntity.ok().body("{Status:Successful");

    }
}
