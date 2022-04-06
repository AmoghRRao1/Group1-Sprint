package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Exceptions.UserDetailsMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class AdminController {
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
}
