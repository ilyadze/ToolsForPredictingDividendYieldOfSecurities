package com.dashko.api.controller;

import com.dashko.api.clients.ApiClient;
import com.dashko.api.dto.wallet.WalletSecurityAddDTO;
import com.dashko.api.dto.wallet.WalletSecurityGetDTO;
import com.dashko.api.mapping.security.SecuritiesGetMapper;
import com.dashko.api.mapping.security.SecuritiesMapper;
import com.dashko.common.service.person.IPersonService;
import com.dashko.common.service.security.ISecuritiesService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person/{email}/securities")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class WalletController {
    ISecuritiesService securityService;

    IPersonService personService;

    SecuritiesMapper addMapper;

    SecuritiesGetMapper getMapper;

    ApiClient apiClient;


    @PostMapping()
    public ResponseEntity<?> addSecurityToUser(
            @PathVariable String email,
            @RequestBody WalletSecurityAddDTO security
            ) {
        System.out.println("click");
        Long personId = personService.getPersonByEmail(email).getId();
        securityService.addOrUpdateSecurity(personId, addMapper.map(security));
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<WalletSecurityGetDTO>> getPersonSecurities(@PathVariable String email) {
        Long personId = personService.getPersonByEmail(email).getId();
        List<WalletSecurityGetDTO> securities = securityService.getPersonSecurities(personId)
                .stream().map(security -> getMapper.map(security)).toList();
        securities.forEach(element -> {
            element.setActualPrice(apiClient.getPrice(element.getSymbol()));
            element.setPriceChange((element.getActualPrice() - element.getPrice())*100/ element.getPrice());
        });
        return ResponseEntity.ok(securities);
    }
}
