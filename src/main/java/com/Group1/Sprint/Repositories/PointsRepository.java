package com.Group1.Sprint.Repositories;


import com.Group1.Sprint.Models.PointsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointsRepository extends JpaRepository<PointsModel,Integer> {
    List<PointsModel> findByTournamentId(int tournamentId);
}
