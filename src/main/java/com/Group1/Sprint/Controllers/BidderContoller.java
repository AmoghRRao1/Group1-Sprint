package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Models.BidderModel;
import com.Group1.Sprint.Repositories.BidderRepository;
import com.Group1.Sprint.Services.IBidderService;
import com.Group1.Sprint.Services.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bidder")
@CrossOrigin(origins = "http://localhost:3000")
public class BidderContoller {

    @Autowired
    IBidderService bidderService;

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    BidderRepository bidderRepository;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> loginDetails)
    {
        Map<String, String> response = new HashMap<>();

        try
        {
            if(bidderService.login(loginDetails))
            {
                String to =this.jwTutil.generateToken(new User(loginDetails.get("email"),loginDetails.get("password"),new ArrayList<>()));
                response.put("Status","Successful");
                String bidderID = String.valueOf(bidderRepository.findByEmail(loginDetails.get("email")).get().getBidderId());
                response.put("BidderID",bidderID);
                response.put("Token",to);

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


    @PostMapping("/{id}/bid")
    public ResponseEntity<Map<String, String>> bid(@RequestBody Map<String,Integer> bidDetails, @PathVariable(value = "id") int bidderId)
    {
        Map<String, String> response = new HashMap<>();
        try {
            if (bidderService.bid(bidDetails, bidderId)) {
                response.put("Status","Successful");
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

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody BidderModel userDetails)
    {
        Map<String, String> response = new HashMap<>();
        try
        {
            if(bidderService.register(userDetails))
            {
                String to =this.jwTutil.generateToken(new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>()));
                response.put("Status","Successful");
                response.put("Token",to);
                //response.put("SessionID","1234");
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
    @GetMapping("/showleaderboard")
    public List<BidderModel> showLeaderboard()
    {
        return bidderService.showLeaderBoard();
    }

}

