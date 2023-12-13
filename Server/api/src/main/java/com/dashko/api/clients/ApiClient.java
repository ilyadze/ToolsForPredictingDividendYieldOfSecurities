package com.dashko.api.clients;


import com.dashko.common.dto.charts.DividendsApiResponse;
import com.dashko.common.dto.charts.SecurityPriceGetDTO;
import com.dashko.common.dto.news.NewsApiResponse;
import com.dashko.common.dto.securities.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiClient {

    @Value("${api.url}")
    String URL;
    @Value("${api.token}")
    String API_TOKEN;
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

    public List<SecuritiesGetDTO> getSecuritiesWithFilter() {
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
        return restTemplate.exchange(URL + "/v3/search?query=" + query + "&limit=5" + "&" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesSearchDTO>>() {})
                .getBody();

    }

    @Cacheable("newsCache")
    public NewsApiResponse getNews(Integer page) {
        return restTemplate.exchange(URL + "/v3/fmp/articles?page=" + page + "&size=10" + "&" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<NewsApiResponse>() {})
                .getBody();
    }


    public List<SecurityPriceGetDTO> getPrices(String symbol, String time, String from, String to) {
        return restTemplate.exchange(URL + "/v3/historical-chart/" + time +
                                "/" + symbol +
                                "?from=" + from + "&to=" + to +
                                "&" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecurityPriceGetDTO>>() {})
                .getBody();
    }


    public DividendsApiResponse getDividends(String symbol, String from, String to) {
        return restTemplate.exchange(URL + "/v3/historical-price-full/stock_dividend/"
                                + symbol + "?"
                                + "from=" + from + "&to=" + to
                                + "&" + API_TOKEN,
                        org.springframework.http.HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<DividendsApiResponse>() {})
                .getBody();
    }

    public Double getPrice(String symbol) {
        return Objects.requireNonNull(restTemplate.exchange(URL + "/v3/quote-short/"
                                + symbol + "?" + API_TOKEN,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ActualPriceDTO>>() {
                        })
                .getBody()).stream().findFirst().get().getPrice();
    }


    public List<SecuritiesGetDTO> filterSecurities(FilterDTO filters) {
        // Формируем параметры запроса
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("sectors", String.join(",", filters.getSectors()));
        queryParams.put("industries", String.join(",", filters.getIndustries()));
        queryParams.put("countries", String.join(",", filters.getCountries()));
        queryParams.put("priceMoreThan", filters.getPriceMoreThan());
        queryParams.put("priceLowerThan", filters.getPriceLowerThan());


        String urlWithParams = URL + "/v3/stock-screener" + buildQueryString(queryParams) + "&" + API_TOKEN;
        return restTemplate.exchange(urlWithParams,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SecuritiesGetDTO>>() {})
                .getBody();
    }

    // Вспомогательный метод для построения строки запроса
    private String buildQueryString(Map<String, Object> queryParams) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("?");
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        // Удаляем последний символ "&"
        queryString.deleteCharAt(queryString.length() - 1);
        return queryString.toString();
    }
}

