package ru.ifmo.blps.worker.sale;

import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;

public class SaleStrategy implements ListingStrategy<SaleListing> {
    private ListingsService listingsService;

    public SaleStrategy(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void addListing(Listing listing) {
        listingsService.saveSaleListing((SaleListing) listing);
    }

    @Override
    public List<SaleListing> getAllListings() {
        return listingsService.getAllSalesListing();
    }
}
