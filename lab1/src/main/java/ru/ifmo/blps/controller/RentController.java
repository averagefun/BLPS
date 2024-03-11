package ru.ifmo.blps.controller;


import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.RentListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.utils.convertors.RentListingConvertor;
import ru.ifmo.blps.worker.ListingStrategy;
import ru.ifmo.blps.worker.rent.RentStrategy;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rent")
public class RentController {

    private final RentStrategy rentStrategy;

    private final RentListingConvertor rentListingConvertor;

    @Autowired
    public RentController(RentListingConvertor rentListingConvertor, RentStrategy rentStrategy) {
        this.rentListingConvertor = rentListingConvertor;
        this.rentStrategy = rentStrategy;
    }

    @GetMapping("/listings")
    public ResponseEntity<List<RentListing>> getSaleListings() {
        List<RentListing> saleListings = rentStrategy.getAllListings();
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/listings")
    public ResponseEntity<RentListing> createSaleListing(@RequestBody RentListingRequest request) {
        RentListing listing = rentListingConvertor.dto2Model(request);
        rentStrategy.addListing(listing);
        return ResponseEntity.ok(listing);
    }

}
