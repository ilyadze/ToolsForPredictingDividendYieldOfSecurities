package com.dashko.api.controller;

import com.dashko.api.clients.ApiClient;
import com.dashko.api.clients.CurrenciesApiClient;
import com.dashko.api.mapping.security.SecuritiesGetMapper;
import com.dashko.api.mapping.security.SecuritiesMapper;
import com.dashko.common.dto.charts.DividendsValueGetDTO;
import com.dashko.common.dto.wallet.WalletInfoDTO;
import com.dashko.common.dto.wallet.WalletSecurityAddDTO;
import com.dashko.common.dto.wallet.WalletSecurityGetDTO;
import com.dashko.common.models.Person;
import com.dashko.common.models.Security;
import com.dashko.common.service.person.IPersonService;
import com.dashko.common.service.purchase.IPurchaseService;
import com.dashko.common.service.security.ISecuritiesService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    CurrenciesApiClient currenciesApiClient;

    IPurchaseService purchaseService;


    @PostMapping()
    public ResponseEntity<?> addSecurityToUser(
            @PathVariable String email,
            @RequestBody WalletSecurityAddDTO security
            ) {
        Long personId = personService.getPersonByEmail(email).getId();
        securityService.addOrUpdateSecurity(personId, security);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<WalletSecurityGetDTO>> getPersonSecurities(@PathVariable String email) {
        Long personId = personService.getPersonByEmail(email).getId();
        List<WalletSecurityGetDTO> securities = securityService.getPersonSecurities(personId)
                .stream().map(security -> getMapper.map(security)).toList();
        securities.forEach(element -> {
            element.setActualPrice(apiClient.getPrice(element.getSymbol()));
            element.setPriceChange(((element.getActualPrice() - element.getPrice())*100/ element.getPrice()));
        });
        return ResponseEntity.ok(securities);
    }

    @GetMapping("/dividends")
    public ResponseEntity<List<DividendsValueGetDTO>> getPersonSecuritiesDividends(@PathVariable String email) {
        Long personId = personService.getPersonByEmail(email).getId();
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = currentDate.format(formatter);
        List<Security> securities = securityService.getPersonSecurities(personId);
//                .stream().map(security -> getMapper.map(security)).toList();
        return ResponseEntity.ok(apiClient.getDividends(securities.get(0).getSymbol(), purchaseService.getPurchase(securities.get(0).getId()).getPurchaseDate(), formattedDate).getDividends());
    }

    @GetMapping("/wallet")
    public ResponseEntity<WalletInfoDTO> getPersonSecuritiesWallet(@PathVariable String email) {
        Long personId = personService.getPersonByEmail(email).getId();
        List<WalletSecurityGetDTO> securities = securityService.getPersonSecurities(personId)
                .stream().map(security -> getMapper.map(security)).toList();
        securities.forEach(element -> {
            element.setActualPrice(apiClient.getPrice(element.getSymbol()));
        });
        WalletInfoDTO walletInfoDTO = new WalletInfoDTO();
        Double actualPrice = securities.stream().mapToDouble(security -> {
            double currenciesValue = currenciesApiClient.getCurrencies(security.getCurrency(), "USD");
            return security.getActualPrice()*security.getTotalQuantity()*currenciesValue;
        }).sum();
        Double fullPrice = securities.stream().mapToDouble(security -> {
            double currenciesValue = currenciesApiClient.getCurrencies(security.getCurrency(), "USD");
            return security.getTotalPrice()*currenciesValue;
        }).sum();
        return ResponseEntity.ok(walletInfoDTO.builder()
                        .actualPrice(actualPrice)
                        .fullPrice(fullPrice)
                        .priceChange((fullPrice - actualPrice)*100/fullPrice)
                        .currency("USD")
                        .totalSecurities(securities.size())
                .build());
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<?> deleteSecurities(@PathVariable String email, @PathVariable String symbol) {
        Person person = personService.getPersonByEmail(email);
        securityService.deleteSecurity(person, symbol);
        return ResponseEntity.ok().build();
    }
}
