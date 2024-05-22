package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.service.ListingsService;

import java.util.Optional;

@Component
public class CheckListingDelegate implements JavaDelegate {

    private final ListingsService listingsService;

    public CheckListingDelegate(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (!isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN can execute this task.");
        }

        Long userId = (Long) execution.getVariable("userId");
        Optional<RentListing> rentListing = listingsService.getCreatedRentListing(userId);

        if (rentListing.isEmpty()) {
            execution.setVariable("listingExists", false);
        } else {
            execution.setVariable("listingExists", true);
            execution.setVariable("rentListing", rentListing.get());
        }
        execution.setVariable("checkListingResult", "Listing checked successfully");

    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }
}