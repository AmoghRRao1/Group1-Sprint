package com.Group1.Sprint.Controllers;

import com.Group1.Sprint.Models.MatchesModel;
import com.Group1.Sprint.Models.TournamentModel;
import com.Group1.Sprint.Repositories.MatchesRepository;
import com.Group1.Sprint.Repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    MatchesRepository matchesRepository;

    @GetMapping("/tournamentDetails/{id}")
    public List<MatchesModel> tournamentDetails(@PathVariable(value = "id")int tournamentId)
    {
        return matchesRepository.findByTournamentId(tournamentId);
    }
    @GetMapping("/tournaments")
    public List<TournamentModel> tournamentDetails()
    {
        return tournamentRepository.findAll();
    }
}
