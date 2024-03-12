package ru.ifmo.blps.controller;


import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Filter;
import org.openapitools.model.RentListingRequest;
import org.openapitools.model.VerifyListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.exceptions.NotEnoughBalanceException;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.enums.ConformationType;
import ru.ifmo.blps.model.enums.ListingStatus;
import org.openapitools.model.SellerType;
import ru.ifmo.blps.utils.convertors.RentListingConvertor;
import ru.ifmo.blps.worker.rent.RentStrategy;

import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping("/listings/_search")
    public ResponseEntity<List<RentListing>> getSaleListings(@RequestBody Filter filter) {
        List<RentListing> saleListings = rentStrategy.getAllListingsBuFilter(filter);
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/listings")
    public ResponseEntity<RentListing> createSaleListing(@RequestBody RentListingRequest request) {
        RentListing listing = rentListingConvertor.dto2Model(request);
        rentStrategy.addListing(listing);
        return ResponseEntity.ok(listing);
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyRentListing(@RequestBody VerifyListingRequest verifySaleListingRequest) {
        log.info("Анекта от " + verifySaleListingRequest);
        try {
            return ResponseEntity.ok(rentStrategy.verifyListing(verifySaleListingRequest.getSellerType()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmRentListing(@RequestBody String confirmListingRequest) {
        log.info("Подтверждение на аренду типа " + confirmListingRequest);
        try {
            return ResponseEntity.ok(rentStrategy.confirmListing(ConformationType.fromString(confirmListingRequest)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        } catch (NotEnoughBalanceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
