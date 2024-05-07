package ru.ifmo.blps.worker;

import java.util.List;

import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import ru.ifmo.blps.exceptions.NoSuchListingsException;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.ConformationType;

public interface ListingStrategy<T extends Listing> extends SellerTypeMapper{
    void addListing(T listing, User user);

    List<T> getAllListings();

    Integer verifyListing(SellerType sellerType, User user) throws NoSuchListingsException;

    int confirmListing(ConformationType listingStatus, User user) throws NoSuchListingsException;

    List<T> getAllListingsByFilter(Filter filter);
}
