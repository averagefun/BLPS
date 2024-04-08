package ru.ifmo.blps.worker.sale;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.ConformationType;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.service.UsersService;
import ru.ifmo.blps.worker.ListingStrategy;

@Component
public class SaleStrategy implements ListingStrategy<SaleListing> {
    private final ListingsService listingsService;
    private final UsersService usersService;

    @Autowired
    public SaleStrategy(ListingsService listingsService, UsersService usersService) {
        this.listingsService = listingsService;
        this.usersService = usersService;
    }

    @Override
    public void addListing(SaleListing listing) {
        listingsService.deleteSaleListingIfExist(ListingStatus.CREATED);
        listingsService.deleteSaleListingIfExist(ListingStatus.VERIFY);
        listingsService.saveSaleListing(listing);
    }

    @Override
    public List<SaleListing> getAllListings() {
        return listingsService.getAllSalesListing();
    }

    @Override
    public int verifyListing(SellerType sellerType) {
        Optional<SaleListing> saleListing= listingsService.getCreatedSaleListing();
        if (saleListing.isPresent()){
            saleListing.get().setStatus(ListingStatus.VERIFY);
            saleListing.get().setSellerType(sellerType);
            listingsService.saveSaleListing(saleListing.get());
            return listingsService.countSaleListings() < getFreeListings(sellerType)? 0: 100;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int confirmListing(ConformationType listingStatus, User user) {
        Optional<SaleListing> saleListing = listingsService.getVerifiedSaleListing();
        if (saleListing.isPresent()) {
            switch (listingStatus) {
                case DELETE -> listingsService.deleteSaleListing(saleListing.get());
                case BACK -> {
                    saleListing.get().setStatus(ListingStatus.CREATED);
                    listingsService.saveSaleListing(saleListing.get());
                }
                case CONFIRM -> {
                    int cost = listingsService.countSaleListings() < getFreeListings(saleListing.get().getSellerType()) ? 0 : 100;
                    usersService.pay(user, cost);
                    saleListing.get().setStatus(ListingStatus.LISTED);
                    listingsService.saveSaleListing(saleListing.get());
                    return user.getBalance();
                    }
                }
        } else {
            throw new NoSuchElementException();
        }
        return 0;
    }

    @Override
    public List<SaleListing> getAllListingsBuFilter(Filter filter) {
        return listingsService.getSaleListingsByFilter(filter);
    }
}
