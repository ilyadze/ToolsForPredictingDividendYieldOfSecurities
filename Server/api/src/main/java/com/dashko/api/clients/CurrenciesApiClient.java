package com.dashko.api.clients;

import com.dashko.common.dto.securities.CurrencyApiDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrenciesApiClient {
    @Value("${currency-api.url}")
    String URL;

    @Value("${currency-api.token}")
    String TOKEN;

    final RestTemplate restTemplate = new RestTemplate();

    public Double getCurrencies(String fromCurrency, String toCurrency) {

        return Objects.requireNonNull(restTemplate.exchange(URL
                                + TOKEN + "/latest/" + fromCurrency,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CurrencyApiDTO>() {
                        })
                .getBody()).getConversionRates().get(toCurrency);
    }

}
