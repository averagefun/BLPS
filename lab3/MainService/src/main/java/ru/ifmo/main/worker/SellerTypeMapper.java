package ru.ifmo.main.worker;

import org.openapitools.model.SellerType;

interface SellerTypeMapper {

    default int getFreeListings(SellerType sellerType) {
        return sellerType == SellerType.OWNER? 1 : 0;
    }
}