package ru.ifmo.blps.worker;

import ru.ifmo.blps.model.Listing;

import java.util.List;

public interface ListingStrategy<T extends Listing> {
    void addListing(T listing);

    List<T> getAllListings();
}
