package com.dashko.common.repository;

import com.dashko.common.models.Person;
import com.dashko.common.models.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Optional<Security> findByPersonIdAndSymbol(Long personId, String symbol);

    List<Security> findByPersonId(Long personId);

    boolean existsByPersonIdAndSymbol(Long personId, String symbol);

    void deleteByPersonAndSymbol(Person person, String symbol);

}
