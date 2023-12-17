package com.dashko.common.service.security;

import com.dashko.common.dto.wallet.WalletSecurityAddDTO;
import com.dashko.common.models.Person;
import com.dashko.common.models.Security;
import com.dashko.common.models.SecurityPurchase;
import com.dashko.common.repository.PurchaseRepository;
import com.dashko.common.repository.SecurityRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecuritiesService implements ISecuritiesService {
    @Autowired
    SecurityRepository securityRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Override
    public void addOrUpdateSecurity(Long personId, WalletSecurityAddDTO newSecutiry) {
        Optional<Security> existingSecurity = securityRepository.findByPersonIdAndSymbol(personId, newSecutiry.getSymbol());
        if (existingSecurity.isPresent()) {
            Security security = existingSecurity.get();
            security.setTotalPrice(security.getTotalPrice() + newSecutiry.getPrice() * newSecutiry.getQuantity()); // увеличиваем стоимость
            security.setTotalQuantity(security.getTotalQuantity() + newSecutiry.getQuantity()); // увеличиваем количество
            securityRepository.save(security);
        } else {
            Person person = new Person();
            person.setId(personId);
            Security security = new Security();
            security.setName(newSecutiry.getName());
            security.setSymbol(newSecutiry.getSymbol());
            security.setCurrency(newSecutiry.getCurrency());
            security.setPrice(newSecutiry.getPrice());
            security.setTotalPrice(newSecutiry.getPrice() * newSecutiry.getQuantity());
            security.setTotalQuantity(newSecutiry.getQuantity());
            security.setPerson(person);

            securityRepository.save(security);
        }
        Optional<SecurityPurchase> purchase = purchaseRepository.findBySecuritySymbol(newSecutiry.getSymbol());
        if(purchase.isPresent() ) {
            if (purchase.get().getPurchaseDate().equals(newSecutiry.getDateOfPurchase())) {
                SecurityPurchase updatePurchase = purchase.get();
                updatePurchase.setSecurity(securityRepository.findByPersonIdAndSymbol(personId, newSecutiry.getSymbol()).get());
                updatePurchase.setPurchaseDate(newSecutiry.getDateOfPurchase());
                updatePurchase.setQuantity(newSecutiry.getQuantity() + updatePurchase.getQuantity());
                updatePurchase.setPurchasePrice(newSecutiry.getPrice());
                purchaseRepository.save(updatePurchase);
            }
        }
        SecurityPurchase newPurchase = new SecurityPurchase();
        newPurchase.setSecurity(securityRepository.findByPersonIdAndSymbol(personId, newSecutiry.getSymbol()).get());
        newPurchase.setPurchaseDate(newSecutiry.getDateOfPurchase());
        newPurchase.setQuantity(newSecutiry.getQuantity());
        newPurchase.setPurchasePrice(newSecutiry.getPrice());
        purchaseRepository.save(newPurchase);


    }

    @Override
    public List<Security> getPersonSecurities(Long personId) {
        return securityRepository.findByPersonId(personId);
    }

    @Override
    @Transactional
    public void deleteSecurity(Person person, String symbol) {
        if(securityRepository.existsByPersonIdAndSymbol(person.getId(), symbol)) {
            securityRepository.deleteByPersonAndSymbol(person, symbol);
        }
    }
}
