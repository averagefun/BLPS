package ru.ifmo.blps.worker.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;

@Component
public class SaleStrategy implements ListingStrategy<SaleListing> {
    private final ListingsService listingsService;

    @Autowired
    public SaleStrategy(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void addListing(SaleListing listing) {
        listingsService.saveSaleListing(listing);
    }

    @Override
    public List<SaleListing> getAllListings() {
        return listingsService.getAllSalesListing();
    }
}
