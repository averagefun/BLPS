package ru.ifmo.blps.controller;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.SaleListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.utils.convertors.SaleListingConvertor;
import ru.ifmo.blps.worker.ListingStrategy;
import ru.ifmo.blps.worker.sale.SaleStrategy;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/sale")
public class SalesController {

    private final SaleStrategy saleStrategy;

    private final SaleListingConvertor saleListingConvertor;

    @Autowired
    public SalesController(SaleListingConvertor saleListingConvertor, SaleStrategy saleStrategy) {
        this.saleListingConvertor = saleListingConvertor;
        this.saleStrategy = saleStrategy;
    }

    @GetMapping("/listings")
    public ResponseEntity<List<SaleListing>> getSaleListings() {
        List<SaleListing> saleListings = saleStrategy.getAllListings();
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/listings")
    public ResponseEntity<SaleListing> createSaleListing(@RequestBody SaleListingRequest request) {
        SaleListing listing = saleListingConvertor.dto2Model(request);
        saleStrategy.addListing(listing);
        return ResponseEntity.ok(listing);
    }
}
