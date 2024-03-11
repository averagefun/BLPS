package ru.ifmo.blps.worker;

import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.enums.SellerType;

import java.util.List;

public interface ListingStrategy<T extends Listing> {
    void addListing(T listing);

    List<T> getAllListings();

    Integer verifyListing(SellerType sellerType);
}
