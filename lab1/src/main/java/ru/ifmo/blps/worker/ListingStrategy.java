package ru.ifmo.blps.worker;

import org.openapitools.model.Filter;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.enums.ConformationType;
import ru.ifmo.blps.model.enums.ListingStatus;
import org.openapitools.model.SellerType;

import java.util.List;

public interface ListingStrategy<T extends Listing> extends SellerTypeMapper{
    void addListing(T listing);

    List<T> getAllListings();

    Integer verifyListing(SellerType sellerType);

    Integer confirmListing(ConformationType listingStatus);

    public List<T> getAllListingsBuFilter(Filter filter);
}
