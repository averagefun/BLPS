package ru.ifmo.main.worker.rent;

import java.util.List;
import java.util.Optional;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ifmo.main.exceptions.NoSuchListingsException;
import ru.ifmo.main.exceptions.NotEnoughBalanceException;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.model.enums.ConformationType;
import ru.ifmo.main.model.enums.ListingStatus;
import ru.ifmo.main.service.ListingsService;
import ru.ifmo.main.service.UsersService;
import ru.ifmo.main.worker.ListingStrategy;

@Slf4j
@Component
public class RentStrategy implements ListingStrategy<RentListing> {
    private final ListingsService listingsService;
    private final UsersService usersService;
    private final TransactionTemplate transactionTemplate;

    private final static int LISTING_PRICE = 100;
    private final static int FIRST_OWNER_LISTING = 20;

    @Autowired
    public RentStrategy(ListingsService listingsService, UsersService usersService,
                        PlatformTransactionManager transactionManager) {
        this.listingsService = listingsService;
        this.usersService = usersService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void addListing(RentListing listing, User user) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                listingsService.deleteRentListingIfExist(ListingStatus.CREATED, user);
                listingsService.deleteRentListingIfExist(ListingStatus.VERIFY, user);
                listing.setAuthorId(user.getId());
                listingsService.saveRentListing(listing);
            }
        });
    }

    @Override
    public List<RentListing> getAllListings() {
        return listingsService.getAllRentsListing();
    }

    @Override
    public List<RentListing> getAllListingsByFilter(Filter filter) {
        return listingsService.getRentListingsByFilter(filter);
    }

    @Override
    public Integer verifyListing(SellerType sellerType, long userId) throws NoSuchListingsException {
        Integer listings = transactionTemplate.execute(status -> {
            Optional<RentListing> rentListing = listingsService.getCreatedRentListing(userId);
            if (rentListing.isPresent()) {
                rentListing.get().setStatus(ListingStatus.VERIFY);
                rentListing.get().setSellerType(sellerType);
                listingsService.saveRentListing(rentListing.get());
                return listingsService.countRentListings(userId) < getFreeListings(sellerType) ? FIRST_OWNER_LISTING :
                        LISTING_PRICE;
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
            try {
                Optional<RentListing> rentListing = listingsService.getVerifiedRentListing(user.getId());
                if (rentListing.isPresent()) {
                    switch (listingStatus) {
                        case DELETE -> listingsService.deleteRentListing(rentListing.get());
                        case BACK -> {
                            rentListing.get().setStatus(ListingStatus.CREATED);
                            listingsService.saveRentListing(rentListing.get());
                        }
                        case CONFIRM -> {
                            int cost =
                                    listingsService.countRentListings(user.getId()) < getFreeListings(rentListing.get().getSellerType())
                                    ? FIRST_OWNER_LISTING : LISTING_PRICE;
                            rentListing.get().setStatus(ListingStatus.LISTED);
                            listingsService.saveRentListing(rentListing.get());
                            log.info("Update listing");

                            usersService.pay(user, cost);
                            return user.getBalance();
                        }
                    }
                } else {
                    return -1;
                }
            } catch (NotEnoughBalanceException e) {
                log.warn("Rollback rent transaction");
                status.setRollbackOnly();
            }
            return 0;
        });

        if (listings == null || listings < 0) {
            throw new NoSuchListingsException();
        }
        return listings;
    }
}
