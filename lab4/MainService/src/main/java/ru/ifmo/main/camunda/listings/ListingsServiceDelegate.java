package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.service.ListingsService;

@Component("listingsServiceDelegate")
public class ListingsServiceDelegate implements JavaDelegate {

    private final ListingsService listingsService;

    public ListingsServiceDelegate(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        checkAuthentication();

        RentListing listing = (RentListing) execution.getVariable("rentListing");
        User user = (User) execution.getVariable("authorizedUser");
        listingsService.sendListingToVerification(listing, user);
    }

    private void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
    }
}