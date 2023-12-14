package com.dashko.common.service.security;

import com.dashko.common.dto.wallet.WalletSecurityAddDTO;
import com.dashko.common.models.Person;
import com.dashko.common.models.Security;

import java.util.List;

public interface ISecuritiesService {
    public void addOrUpdateSecurity(Long personId, WalletSecurityAddDTO newSecutiry);

    public List<Security> getPersonSecurities(Long personId);

    public void deleteSecurity(Person person, String symbol);
}
