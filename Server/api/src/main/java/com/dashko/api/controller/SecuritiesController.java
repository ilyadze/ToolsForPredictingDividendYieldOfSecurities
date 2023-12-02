package com.dashko.api.controller;


import com.dashko.api.clients.ApiClient;
import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import com.dashko.api.dto.securities.SecuritiesSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
        if (list.size() > 5) {
            return ResponseEntity.ok(list.subList(0, 5));
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
