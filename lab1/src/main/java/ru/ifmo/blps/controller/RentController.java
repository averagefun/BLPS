package ru.ifmo.blps.controller;

import org.openapitools.model.ListingRequest;
import org.openapitools.model.ListingsResponse;
import org.openapitools.model.RentListingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentController {

    @GetMapping("/rent/listings")
    public ResponseEntity<List<ListingsResponse>> getRentListings() {
        // Здесь должна быть логика получения списка объявлений на аренду
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/rent/listings")
    public ResponseEntity<RentListingResponse> createRentListing(@RequestBody ListingRequest request) {
        // Здесь должна быть логика создания объявления на аренду
        return ResponseEntity.ok(new RentListingResponse());
    }


}
