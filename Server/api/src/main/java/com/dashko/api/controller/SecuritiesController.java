package com.dashko.api.controller;


import com.dashko.api.clients.ApiClient;
import com.dashko.common.dto.charts.DividendsValueGetDTO;
import com.dashko.common.dto.charts.SecurityPriceGetDTO;
import com.dashko.common.dto.news.NewsApiResponse;
import com.dashko.common.dto.securities.FilterDTO;
import com.dashko.common.dto.securities.SecuritiesGetDTO;
import com.dashko.common.dto.securities.SecuritiesInfoDTO;
import com.dashko.common.dto.securities.SecuritiesSearchDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/securities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecuritiesController {

    ApiClient apiClient;

    @GetMapping("")
    public ResponseEntity<List<SecuritiesGetDTO>> getSecurities(@RequestParam("from") Integer fromIndex, @RequestParam("to") Integer toIndex) {
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
                                                               @RequestParam("to") String to,
                                                               @RequestParam("range") String range) {
        return ResponseEntity.ok(apiClient.getPrices(symbol, range, from, to));
    }
    @GetMapping("/{symbol}/dividends")
    public ResponseEntity<List<DividendsValueGetDTO>> getDividends(@PathVariable("symbol") String symbol,
                                                                   @RequestParam("from") String from,
                                                                   @RequestParam("to") String to) {
        return ResponseEntity.ok(apiClient.getDividends(symbol, from, to).getDividends());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SecuritiesGetDTO>> getSecuritiesFilter(@RequestParam("from") Integer fromIndex, @RequestParam("to") Integer toIndex, @RequestBody FilterDTO filter) {
        return ResponseEntity.ok(apiClient.filterSecurities(filter).subList(fromIndex, toIndex));
    }
}
