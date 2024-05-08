package ru.ifmo.main.controller;


import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ConfirmListingRequest;
import org.openapitools.model.Filter;
import org.openapitools.model.RentListingRequest;
import org.openapitools.model.VerifyListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.main.exceptions.NoSuchListingsException;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.model.enums.ConformationType;
import ru.ifmo.main.service.ListingsService;
import ru.ifmo.main.service.UsersService;
import ru.ifmo.main.utils.convertors.RentListingConvertor;
import ru.ifmo.main.worker.rent.RentStrategy;

@Slf4j
@RestController
@RequestMapping("/rent")
public class RentController {

    private final UsersService usersService;
    private final RentListingConvertor rentListingConvertor;
    private final RentStrategy rentStrategy;
    private final ListingsService listingsService;

    @Autowired
    public RentController(UsersService usersService, RentListingConvertor rentListingConvertor,
                          RentStrategy rentStrategy, ListingsService listingsService) {
        this.usersService = usersService;
        this.rentListingConvertor = rentListingConvertor;
        this.rentStrategy = rentStrategy;
        this.listingsService = listingsService;
    }

    @GetMapping("/listings")
    public ResponseEntity<List<RentListing>> getRentListings() {
        List<RentListing> saleListings = rentStrategy.getAllListings();
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/listings/search")
    public ResponseEntity<List<RentListing>> getRentListings(@RequestBody Filter filter) {
        List<RentListing> saleListings = rentStrategy.getAllListingsByFilter(filter);
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/listings")
    public ResponseEntity<RentListing> createRentListing(@RequestBody RentListingRequest request) {
        RentListing listing = rentListingConvertor.dto2Model(request);
        User user = usersService.getAuthorizedUser();
        rentStrategy.addListing(listing, user);
        listingsService.sendListingToVerification(listing, user);
        return ResponseEntity.ok(listing);
    }

    @Secured("ADMIN")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyRentListing(@RequestBody VerifyListingRequest verifySaleListingRequest) {
        log.info("Анекта от " + verifySaleListingRequest);
        try {
            return ResponseEntity.ok(rentStrategy.verifyListing(verifySaleListingRequest.getSellerType(),
                    verifySaleListingRequest.getUserId()));
        } catch (NoSuchListingsException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmRentListing(@RequestBody ConfirmListingRequest confirmListingRequest) {
        log.info("Подтверждение на аренду типа " + confirmListingRequest);
        try {
            return ResponseEntity.ok(rentStrategy.confirmListing(ConformationType.fromString(confirmListingRequest.name()),
                    usersService.getAuthorizedUser()));
        } catch (NoSuchListingsException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
