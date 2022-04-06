package com.Group1.Sprint.Services;

import com.Group1.Sprint.Models.BidderModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IBidderService {
    public boolean bid(Map<String,Integer> bidDetails,int bidderId);
}
