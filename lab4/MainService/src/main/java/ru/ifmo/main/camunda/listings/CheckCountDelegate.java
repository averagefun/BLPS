package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openapitools.model.SellerType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.service.ListingsService;



@Component
public class CheckCountDelegate implements JavaDelegate {

    private final static int LISTING_PRICE = 100;
    private final static int FIRST_OWNER_LISTING = 20;

    private final ListingsService listingsService;

    public CheckCountDelegate(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (!isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN can execute this task.");
        }

        Long userId = (Long) execution.getVariable("userId");
        String type = (String) execution.getVariable("sellerType");
        SellerType sellerType = SellerType.valueOf(type);

        int count = listingsService.countRentListings(userId);
        int freeListings = getFreeListings(sellerType);

        if (count < freeListings) {
            execution.setVariable("listingPrice", FIRST_OWNER_LISTING);
            execution.setVariable("listingCost", FIRST_OWNER_LISTING);
        } else {
            execution.setVariable("listingPrice", LISTING_PRICE);
            execution.setVariable("listingCost", LISTING_PRICE);

        }
    }

    private int getFreeListings(SellerType sellerType) {
        if (sellerType == SellerType.OWNER) {
            return 1;
        } else {
            return 0;
        }
    }
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }
}