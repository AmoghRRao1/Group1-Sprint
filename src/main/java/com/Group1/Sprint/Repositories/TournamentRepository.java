package com.Group1.Sprint.Repositories;


import com.Group1.Sprint.Models.TournamentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<TournamentModel, Integer> {
}
