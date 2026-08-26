package com.arnav.ecommerce.repository;

import com.arnav.ecommerce.model.CoPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CoPurchaseRepository extends JpaRepository<CoPurchase, Long> {
    List<CoPurchase> findAll();
    Optional<CoPurchase> findByProductIdAAndProductIdB(Long productIdA, Long productIdB);
}