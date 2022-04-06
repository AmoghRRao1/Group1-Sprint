package com.Group1.Sprint.Repositories;


import com.Group1.Sprint.Models.BidderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidderRepository extends JpaRepository<BidderModel, Integer> {
    Optional<BidderModel> findByEmail(String email);
}
