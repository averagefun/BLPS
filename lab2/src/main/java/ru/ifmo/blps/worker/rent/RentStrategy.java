package ru.ifmo.blps.worker.rent;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.openapitools.model.Filter;
import org.openapitools.model.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.ConformationType;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.service.ListingsService;
import ru.ifmo.blps.service.UsersService;
import ru.ifmo.blps.worker.ListingStrategy;

@Component
public class RentStrategy implements ListingStrategy<RentListing> {
    private final ListingsService listingsService;
    private final UsersService usersService;
    private final TransactionTemplate transactionTemplate;

    private final static int LISTING_PRICE = 100;
    private final static int FIRST_OWNER_LISTING = 0;

    @Autowired
    public RentStrategy(ListingsService listingsService, UsersService usersService,
                        PlatformTransactionManager transactionManager) {
        this.listingsService = listingsService;
        this.usersService = usersService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void addListing(RentListing listing) {
        listingsService.deleteRentListingIfExist(ListingStatus.CREATED);
        listingsService.deleteRentListingIfExist(ListingStatus.VERIFY);
        listingsService.saveRentListing(listing);
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
//                listingsService.deleteRentListingIfExist(ListingStatus.CREATED);
//                listingsService.deleteRentListingIfExist(ListingStatus.VERIFY);
//                listingsService.saveRentListing(listing);
//            }
//        });
    }

    @Override
    public List<RentListing> getAllListings() {
        return listingsService.getAllRentsListing();
    }

    @Override
    public List<RentListing> getAllListingsBuFilter(Filter filter) {
        return listingsService.getRentListingsByFilter(filter);
    }

    @Override
    public int verifyListing(SellerType sellerType) {
        Optional<RentListing> rentListing = listingsService.getCreatedRentListing();
        if (rentListing.isPresent()) {
            rentListing.get().setStatus(ListingStatus.VERIFY);
            rentListing.get().setSellerType(sellerType);
            listingsService.saveRentListing(rentListing.get());
            return listingsService.countRentListings() < getFreeListings(sellerType) ? FIRST_OWNER_LISTING : LISTING_PRICE;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int confirmListing(ConformationType listingStatus, User user) {
        Optional<RentListing> rentListing = listingsService.getVerifiedRentListing();
        if (rentListing.isPresent()) {
            switch (listingStatus) {
                case DELETE -> listingsService.deleteRentListing(rentListing.get());
                case BACK -> {
                    rentListing.get().setStatus(ListingStatus.CREATED);
                    listingsService.saveRentListing(rentListing.get());
                }
                case CONFIRM -> {
                    int cost = listingsService.countRentListings() < getFreeListings(rentListing.get().getSellerType()) ? FIRST_OWNER_LISTING : LISTING_PRICE;
                    usersService.pay(user, cost);
                    rentListing.get().setStatus(ListingStatus.LISTED);
                    listingsService.saveRentListing(rentListing.get());
                    return user.getBalance();
                }
            }
        } else throw new NoSuchElementException();
        return 0;
    }
}
