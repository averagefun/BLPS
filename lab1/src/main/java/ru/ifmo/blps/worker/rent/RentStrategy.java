package ru.ifmo.blps.worker.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.model.enums.SellerType;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class RentStrategy implements ListingStrategy<RentListing> {
    private final ListingsService listingsService;

    @Autowired
    public RentStrategy(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void addListing(RentListing listing) {
        listingsService.deleteRentListingIfExist(ListingStatus.CREATED);
        listing.setSellerType(SellerType.OWNER);
        listingsService.saveRentListing(listing);
    }

    @Override
    public List<RentListing> getAllListings() {
        return listingsService.getAllRentsListing();
    }

    @Override
    public Integer verifyListing(SellerType sellerType) {
        Optional<RentListing> rentListing= listingsService.getCreatedRentListing();
        if (rentListing.isPresent()){
            return listingsService.countRentListings() < sellerType.getFreeListings()? 0: 100;
        }
        throw new NoSuchElementException();
    }
}
