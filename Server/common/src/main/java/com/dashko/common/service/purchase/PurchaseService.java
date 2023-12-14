package com.dashko.common.service.purchase;

import com.dashko.common.models.SecurityPurchase;
import com.dashko.common.repository.PurchaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService implements IPurchaseService{
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public SecurityPurchase getPurchase(Long id) {
        return purchaseRepository.findBySecurityId(id).orElseThrow(EntityNotFoundException::new);
    }
}
