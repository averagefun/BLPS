package ru.ifmo.blps.worker.sale;

import java.util.List;
import java.util.Optional;

import io.micrometer.common.lang.NonNull;
import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ifmo.blps.exceptions.NoSuchListingsException;
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
    private final TransactionTemplate transactionTemplate;


    @Autowired
    public SaleStrategy(ListingsService listingsService, UsersService usersService,
                        PlatformTransactionManager transactionManager) {
        this.listingsService = listingsService;
        this.usersService = usersService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void addListing(SaleListing listing, User user) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                listingsService.deleteSaleListingIfExist(ListingStatus.CREATED);
                listingsService.deleteSaleListingIfExist(ListingStatus.VERIFY);
                listing.setAuthorId(user.getId());
                listingsService.saveSaleListing(listing);
            }
        });
    }

    @Override
    public List<SaleListing> getAllListings() {
        return listingsService.getAllSalesListing();
    }

    @Override
    public Integer verifyListing(SellerType sellerType, User user) throws NoSuchListingsException {
        Integer listings = transactionTemplate.execute(status -> {
            Optional<SaleListing> saleListing = listingsService.getCreatedSaleListing();
            if (saleListing.isPresent()) {
                saleListing.get().setStatus(ListingStatus.VERIFY);
                saleListing.get().setSellerType(sellerType);
                listingsService.saveSaleListing(saleListing.get());
                return listingsService.countSaleListings() < getFreeListings(sellerType) ? 0 : 100;
            }
            return 0;
        });

        if (listings == null || listings <= 0) {
            throw new NoSuchListingsException();
        }
        return listings;
    }

    @Override
    public int confirmListing(ConformationType listingStatus, User user) throws NoSuchListingsException {
        Integer listings = transactionTemplate.execute(status -> {
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
            }
            return 0;
        });

        if (listings == null || listings <= 0) {
            throw new NoSuchListingsException();
        }
        return listings;
    }

    @Override
    public List<SaleListing> getAllListingsByFilter(Filter filter) {
        return listingsService.getSaleListingsByFilter(filter);
    }
}
