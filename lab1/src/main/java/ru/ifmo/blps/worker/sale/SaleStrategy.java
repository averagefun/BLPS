package ru.ifmo.blps.worker.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.model.enums.SellerType;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.worker.ListingStrategy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class SaleStrategy implements ListingStrategy<SaleListing> {
    private final ListingsService listingsService;

    @Autowired
    public SaleStrategy(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void addListing(SaleListing listing) {
        listingsService.deleteSaleListingIfExist(ListingStatus.CREATED);
        listingsService.saveSaleListing(listing);
    }

    @Override
    public List<SaleListing> getAllListings() {
        return listingsService.getAllSalesListing();
    }

    @Override
    public Integer verifyListing(SellerType sellerType) {
        Optional<SaleListing> saleListing= listingsService.getCreatedSaleListing();
        if (saleListing.isPresent()){
            return listingsService.countSaleListings() < sellerType.getFreeListings()? 0: 100;
        }
        throw new NoSuchElementException();
    }
}
