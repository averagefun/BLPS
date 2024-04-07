package ru.ifmo.blps.worker.rent;

import org.openapitools.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.exceptions.NotEnoughBalanceException;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.enums.ConformationType;
import ru.ifmo.blps.model.enums.ListingStatus;
import org.openapitools.model.SellerType;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.service.UserService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class RentStrategy implements ListingStrategy<RentListing> {
    private final ListingsService listingsService;

    private final UserService userService;

    private final Integer LISTING_PRICE = 100;

    private final Integer FIRST_OWNER_LISTING = 0;

    @Autowired
    public RentStrategy(ListingsService listingsService, UserService userService) {
        this.listingsService = listingsService;
        this.userService = userService;
    }

    @Override
    public void addListing(RentListing listing) {
        listingsService.deleteRentListingIfExist(ListingStatus.CREATED);
        listingsService.deleteRentListingIfExist(ListingStatus.VERIFY);
        listingsService.saveRentListing(listing);
    }

    @Override
    public List<RentListing> getAllListings() {
        return listingsService.getAllRentsListing();
    }

    @Override
    public List<RentListing> getAllListingsBuFilter(Filter filter) {
        return listingsService.getRentListingsByFilter(filter);
    }

    @Override
    public Integer verifyListing(SellerType sellerType) {
        Optional<RentListing> rentListing = listingsService.getCreatedRentListing();
        if (rentListing.isPresent()) {
            rentListing.get().setStatus(ListingStatus.VERIFY);
            rentListing.get().setSellerType(sellerType);
            listingsService.saveRentListing(rentListing.get());
            return listingsService.countRentListings() < getFreeListings(sellerType) ? FIRST_OWNER_LISTING : LISTING_PRICE;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Integer confirmListing(ConformationType listingStatus) {
        Optional<RentListing> rentListing = listingsService.getVerifiedRentListing();
        if (rentListing.isPresent()) {
            switch (listingStatus) {
                case DELETE -> listingsService.deleteRentListing(rentListing.get());
                case BACK -> {
                    rentListing.get().setStatus(ListingStatus.CREATED);
                    listingsService.saveRentListing(rentListing.get());
                }
                case CONFIRM -> {
                    int cost = listingsService.countRentListings() < getFreeListings(rentListing.get().getSellerType()) ? FIRST_OWNER_LISTING : LISTING_PRICE;
                    if (!userService.checkBalance(cost)) throw new NotEnoughBalanceException();
                    else {
                        userService.payFromBalance(cost);
                        rentListing.get().setStatus(ListingStatus.LISTED);
                        listingsService.saveRentListing(rentListing.get());
                        return userService.getBalance();
                    }
                }
            }
        } else throw new NoSuchElementException();
        return 0;
    }
}
