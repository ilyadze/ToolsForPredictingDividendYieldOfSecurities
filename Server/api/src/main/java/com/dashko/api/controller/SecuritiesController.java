package com.dashko.api.controller;


import com.dashko.api.clients.ApiClient;
import com.dashko.api.dto.charts.DividendsValueGetDTO;
import com.dashko.api.dto.charts.SecurityPriceGetDTO;
import com.dashko.api.dto.news.NewsApiResponse;
import com.dashko.api.dto.news.NewsGetDTO;
import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import com.dashko.api.dto.securities.SecuritiesSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/securities")
@RequiredArgsConstructor
public class SecuritiesController {

    private final ApiClient apiClient;

    @GetMapping("")
    public ResponseEntity<List<SecuritiesGetDTO>> getSecurities(@RequestParam("from") Integer fromIndex,@RequestParam("to") Integer toIndex) {
        return ResponseEntity.ok(apiClient.getSecurities().subList(fromIndex, toIndex));
    }

    @GetMapping("/length")
    public ResponseEntity<Integer> getSecuritiesLength() {
        return ResponseEntity.ok(apiClient.getSecurities().size());
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<SecuritiesInfoDTO> getSecuritiesInfo(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(apiClient.getSecuritiesInfo(symbol));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SecuritiesSearchDTO>> searchSecurities(@RequestParam("query") String query) {
        if (query.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<SecuritiesSearchDTO> list = apiClient.searchSecurities(query);
        return ResponseEntity.ok(list);

    }

    @GetMapping("/news")
    public ResponseEntity<NewsApiResponse> getNews(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(apiClient.getNews(page));
    }

    @GetMapping("/{symbol}/price")
    public ResponseEntity<List<SecurityPriceGetDTO>> getPrices(@PathVariable("symbol") String symbol,
                                                               @RequestParam("from") String from,
                                                               @RequestParam("to") String to) {
        return ResponseEntity.ok(apiClient.getPrices(symbol, "45min", from, to));
    }
    @GetMapping("/{symbol}/dividends")
    public ResponseEntity<List<DividendsValueGetDTO>> getDividends(@PathVariable("symbol") String symbol,
                                                                   @RequestParam("from") String from,
                                                                   @RequestParam("to") String to) {
        return ResponseEntity.ok(apiClient.getDividends(symbol, from, to).getDividends());
    }
}
