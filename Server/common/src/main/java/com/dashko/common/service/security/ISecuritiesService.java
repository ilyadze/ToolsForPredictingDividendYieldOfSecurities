package com.dashko.common.service.security;

import com.dashko.common.models.Security;

import java.util.List;

public interface ISecuritiesService {
    public void addOrUpdateSecurity(Long personId, Security newSecutiry);

    public List<Security> getPersonSecurities(Long personId);
}
