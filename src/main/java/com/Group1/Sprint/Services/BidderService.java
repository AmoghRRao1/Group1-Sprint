package com.Group1.Sprint.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
