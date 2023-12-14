package com.dashko.common.repository;

import com.dashko.common.models.SecurityPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<SecurityPurchase, Long> {
    Optional<SecurityPurchase> findBySecurityId(Long id);
}
