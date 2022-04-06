package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Services.IBidderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bidder")
public class BidderContoller {

    @Autowired
    IBidderService bidderService;

}
