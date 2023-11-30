package com.dashko.api.clients;

import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
        return restTemplate.exchange(URL + "/stable/ref-data/symbols" + "?" + API_TOKEN,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SecuritiesGetDTO>>() {})
                .getBody();

    }

    public List<SecuritiesGetDTO> getSecuritiesSubList(Integer from, Integer to) {
        List<SecuritiesGetDTO> list = restTemplate.exchange(URL + "/stable/ref-data/symbols" + "?" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesGetDTO>>() {})
                .getBody().subList(from, to );
        for(SecuritiesGetDTO el: list) {
            el.setLogo(getSecurityLogo(el.getSymbol()));
        }
        return list;
    }

    public SecuritiesInfoDTO getSecuritiesInfo(String symbol) {
        SecuritiesInfoDTO dto = restTemplate.exchange(URL + "/stable/stock/" + symbol + "/company?" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<SecuritiesInfoDTO>() {})
                .getBody();
        dto.setLogo(getSecurityLogo(symbol));
        return dto;

    }

    public String getSecurityLogo(String symbol) {
        String logo = restTemplate.getForObject(URL + "/stable/stock/" + symbol + "/logo?" + API_TOKEN, String.class).substring(8);
        return logo.substring(0, logo.length() - 2);
    }


}

