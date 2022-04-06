package com.Group1.Sprint.Repositories;


import com.Group1.Sprint.Models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamModel, Integer> {
}

