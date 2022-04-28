package com.Group1.Sprint.Repositories;


import com.Group1.Sprint.Models.BidsModel;
import com.Group1.Sprint.Models.MatchesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MatchesRepository extends JpaRepository<MatchesModel, Integer> {
    List<MatchesModel> findByTournamentId(int tournamentId);
}
