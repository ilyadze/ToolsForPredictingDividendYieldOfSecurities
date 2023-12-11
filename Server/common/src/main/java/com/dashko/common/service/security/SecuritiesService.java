package com.dashko.common.service.security;

import com.dashko.common.models.Person;
import com.dashko.common.models.Security;
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
    @Override
    public void addOrUpdateSecurity(Long personId, Security newSecutiry) {
        Optional<Security> existingSecurity = securityRepository.findByPersonIdAndSymbol(personId, newSecutiry.getSymbol());
        System.out.println(existingSecurity);
        if (existingSecurity.isPresent()) {
            // Если Security существует, обновляем его поля
            Security security = existingSecurity.get();
            security.setTotalPrice(security.getTotalPrice() + newSecutiry.getPrice() * newSecutiry.getQuantity()); // увеличиваем стоимость
            security.setQuantity(security.getQuantity() + newSecutiry.getQuantity()); // увеличиваем количество
            securityRepository.save(security);
        } else {
            // Если Security не существует, создаем новый
            Person person = new Person();
            person.setId(personId);
            Security security = new Security();
            security.setName(newSecutiry.getName());
            security.setSymbol(newSecutiry.getSymbol());
            security.setCurrency(newSecutiry.getCurrency());
            security.setDateOfPurchase(newSecutiry.getDateOfPurchase());
            security.setPrice(newSecutiry.getPrice());
            security.setTotalPrice(newSecutiry.getPrice() * newSecutiry.getQuantity());
            security.setQuantity(newSecutiry.getQuantity());
            security.setPerson(person);

            securityRepository.save(security);
        }
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
