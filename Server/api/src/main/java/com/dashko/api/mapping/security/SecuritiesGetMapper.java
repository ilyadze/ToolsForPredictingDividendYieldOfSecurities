package com.dashko.api.mapping.security;


import com.dashko.common.dto.wallet.WalletSecurityGetDTO;
import com.dashko.common.models.Security;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  builder = @Builder(disableBuilder = true))
public interface SecuritiesGetMapper {
    WalletSecurityGetDTO map(Security security);

    @InheritInverseConfiguration
    Security map(WalletSecurityGetDTO dto);
}
