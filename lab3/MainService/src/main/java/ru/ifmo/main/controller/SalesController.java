package ru.ifmo.main.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Filter;
import org.openapitools.model.SaleListingRequest;
import org.openapitools.model.VerifyListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.main.exceptions.NoSuchListingsException;
import ru.ifmo.main.exceptions.NotEnoughBalanceException;
import ru.ifmo.main.model.SaleListing;
import ru.ifmo.main.model.enums.ConformationType;
import ru.ifmo.main.service.UsersService;
import ru.ifmo.main.utils.convertors.SaleListingConvertor;
import ru.ifmo.main.worker.sale.SaleStrategy;


@Slf4j
@RestController
@RequestMapping("/sale")
public class SalesController {
    private final SaleStrategy saleStrategy;
    private final UsersService usersService;
    private final SaleListingConvertor saleListingConvertor;

    @Autowired
    public SalesController(SaleListingConvertor saleListingConvertor, SaleStrategy saleStrategy,
                           UsersService usersService) {
        this.saleListingConvertor = saleListingConvertor;
        this.saleStrategy = saleStrategy;
        this.usersService = usersService;
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
        saleStrategy.addListing(listing, usersService.getAuthorizedUser());
        return ResponseEntity.ok(listing);
    }

    @PostMapping("/listings/search")
    public ResponseEntity<List<SaleListing>> getSaleListings(@RequestBody Filter filter) {
        List<SaleListing> saleListings = saleStrategy.getAllListingsByFilter(filter);
        log.info("Получено " + saleListings.size() + " объявлений");
        return ResponseEntity.ok(saleListings);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyRentListing(@RequestBody VerifyListingRequest verifySaleListingRequest) {
        log.info("Анекта от " + verifySaleListingRequest);
        try {
            return ResponseEntity.ok(saleStrategy.verifyListing(verifySaleListingRequest.getSellerType(),
                    usersService.getAuthorizedUser()));
        } catch (NoSuchListingsException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmSaleListing(@RequestBody String confirmListingRequest) {
        log.info("Подтверждение на продажу типа " + confirmListingRequest);
        try {
            return ResponseEntity.ok(saleStrategy.confirmListing(ConformationType.fromString(confirmListingRequest),
                    usersService.getAuthorizedUser()));
        } catch (NoSuchListingsException e) {
            return ResponseEntity.badRequest().body("Не найдены созданные объявления");
        } catch (NotEnoughBalanceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
