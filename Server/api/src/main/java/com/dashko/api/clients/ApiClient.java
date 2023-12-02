package com.dashko.api.clients;

import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import com.dashko.api.dto.securities.SecuritiesSearchDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiClient {

    @Value("${api.url}")
    private  String URL;
    @Value("${api.token}")
    private  String API_TOKEN;
    final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("securitiesCache")
    public List<SecuritiesGetDTO> getSecurities() {
        List<SecuritiesGetDTO> securitiesGetDTOList = restTemplate.exchange(URL + "/v3/stock/list" + "?" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesGetDTO>>() {})
                .getBody();
        return securitiesGetDTOList.stream()
                .filter(security -> !"etf".equals(security.getType()))
                .collect(Collectors.toList());

    }

    public SecuritiesInfoDTO getSecuritiesInfo(String symbol) {
        SecuritiesInfoDTO dto = restTemplate.exchange(URL + "/v3/profile/" + symbol + "?" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesInfoDTO>>() {})
                .getBody().stream().findFirst().get();
        return dto;

    }

    public List<SecuritiesSearchDTO> searchSecurities(String query) {
        return restTemplate.exchange(URL + "/v3/search?query=" + query + "&" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesSearchDTO>>() {})
                .getBody();

    }

}

