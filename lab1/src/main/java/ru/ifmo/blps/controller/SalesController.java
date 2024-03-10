package ru.ifmo.blps.controller;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ListingRequest;
import org.openapitools.model.ListingsResponse;
import org.openapitools.model.SaleListingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class SalesController {

    @GetMapping("/sale/listings")
    public ResponseEntity<List<ListingsResponse>> getSaleListings() {
        // Здесь должна быть логика получения списка объявлений на продажу
        log.info("HIII");
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/sale/listings")
    public ResponseEntity<SaleListingResponse> createSaleListing(@RequestBody ListingRequest request) {
        // Здесь должна быть логика создания объявления на продажу
        return ResponseEntity.ok(new SaleListingResponse());
    }
}
