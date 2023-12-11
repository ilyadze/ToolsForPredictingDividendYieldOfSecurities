package com.dashko.api.clients;

import com.dashko.api.dto.charts.DividendsApiResponse;
import com.dashko.api.dto.charts.SecurityPriceGetDTO;
import com.dashko.api.dto.news.NewsApiResponse;
import com.dashko.api.dto.news.NewsGetDTO;
import com.dashko.api.dto.securities.ActualPriceDTO;
import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import com.dashko.api.dto.securities.SecuritiesSearchDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        System.out.println(URL + "/v3/historical-chart/" + time +
                "/" + symbol +
                "?from=" + from + "&to=" + to +
                "&" + API_TOKEN);
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

}

