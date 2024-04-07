package ru.ifmo.blps.worker;

import org.openapitools.model.SellerType;
import org.openapitools.model.VerifyListingRequest;

interface SellerTypeMapper {

    default int getFreeListings(SellerType sellerType) {
        return sellerType == SellerType.OWNER? 1 : 0;
    }
}