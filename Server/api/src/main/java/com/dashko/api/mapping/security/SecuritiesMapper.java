package com.dashko.api.mapping.security;

import com.dashko.common.dto.wallet.WalletSecurityAddDTO;
import com.dashko.common.models.Security;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring",  builder = @Builder(disableBuilder = true))
public interface SecuritiesMapper {

    WalletSecurityAddDTO map(Security security);

    @InheritInverseConfiguration
    Security map(WalletSecurityAddDTO dto);
}