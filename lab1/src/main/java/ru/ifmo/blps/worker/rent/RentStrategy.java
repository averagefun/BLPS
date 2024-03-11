package ru.ifmo.blps.worker.rent;

import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;

public class RentStrategy implements ListingStrategy<RentListing> {
    private final ListingsService listingsService;

    public RentStrategy(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void addListing(Listing listing) {
        listingsService.saveRentListing((RentListing) listing);
    }

    @Override
    public List<RentListing> getAllListings() {
        return listingsService.getAllRentsListing();
    }
}
