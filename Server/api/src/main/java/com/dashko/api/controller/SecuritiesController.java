package com.dashko.api.controller;


import com.dashko.api.clients.ApiClient;
import com.dashko.api.dto.securities.SecuritiesGetDTO;
import com.dashko.api.dto.securities.SecuritiesInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/securities")
@RequiredArgsConstructor
@CrossOrigin
public class SecuritiesController {

    private final ApiClient apiClient;

    private final Integer SECURITIES_SIZE = 11019;


    @GetMapping("")
    public ResponseEntity<List<SecuritiesGetDTO>> getSecurities(@RequestParam("from") Integer fromIndex,@RequestParam("to") Integer toIndex) {
        return ResponseEntity.ok(apiClient.getSecuritiesSubList(fromIndex, toIndex));
    }

    @GetMapping("/length")
    public ResponseEntity<Integer> getSecuritiesLength() {
        return ResponseEntity.ok(SECURITIES_SIZE);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<SecuritiesInfoDTO> getSecuritiesInfo(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(apiClient.getSecuritiesInfo(symbol));
    }
}
