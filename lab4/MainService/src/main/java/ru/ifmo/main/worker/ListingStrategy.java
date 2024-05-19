package ru.ifmo.main.worker;

import java.util.List;

import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import ru.ifmo.main.exceptions.NoSuchListingsException;
import ru.ifmo.main.model.Listing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.model.enums.ConformationType;

public interface ListingStrategy<T extends Listing> extends SellerTypeMapper{
    void addListing(T listing, User user);

    List<T> getAllListings();

    Integer verifyListing(SellerType sellerType, long userId) throws NoSuchListingsException;

    int confirmListing(ConformationType listingStatus, User user) throws NoSuchListingsException;

    List<T> getAllListingsByFilter(Filter filter);
}
