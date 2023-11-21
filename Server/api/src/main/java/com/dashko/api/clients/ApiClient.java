package com.dashko.api.clients;

import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ApiClient {

    @Value("${api.url}")
    private  String URL;
    @Value("${api.token}")
    private  String API_TOKEN;
    final RestTemplate restTemplate = new RestTemplate();

    public List<SecuritiesGetDTO> getSecurities() {
        return restTemplate.exchange(URL + "/stable/ref-data/symbols" + "?token=" + API_TOKEN,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SecuritiesGetDTO>>() {})
                .getBody();

    }

    public SecuritiesInfoDTO getSecuritiesInfo(String symbol) {
        return restTemplate.exchange(URL + "/stable/stock/" + symbol + "/company?token=" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<SecuritiesInfoDTO>() {})
                        .getBody();

    }
}

