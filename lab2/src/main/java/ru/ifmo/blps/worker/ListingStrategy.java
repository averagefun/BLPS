package ru.ifmo.blps.worker;

import java.util.List;

import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.ConformationType;

public interface ListingStrategy<T extends Listing> extends SellerTypeMapper{
    void addListing(T listing);

    List<T> getAllListings();

    int verifyListing(SellerType sellerType);

    int confirmListing(ConformationType listingStatus, User user);

    List<T> getAllListingsBuFilter(Filter filter);
}
