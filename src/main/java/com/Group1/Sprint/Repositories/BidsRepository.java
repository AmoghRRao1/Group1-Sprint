package com.Group1.Sprint.Repositories;

import com.Group1.Sprint.Models.BidsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidsRepository extends JpaRepository<BidsModel,Integer> {
    List<BidsModel> findByMatchId(int match);
}
