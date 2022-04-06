package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Services.IBidderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bidder")
public class BidderContoller {

    @Autowired
    IBidderService bidderService;

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

}
