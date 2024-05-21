package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.worker.rent.RentStrategy;

@Component("rentStrategyDelegate")
public class RentStrategyDelegate implements JavaDelegate {

    private final RentStrategy rentStrategy;

    public RentStrategyDelegate(RentStrategy rentStrategy) {
        this.rentStrategy = rentStrategy;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        checkAuthentication();

        RentListing listing = (RentListing) execution.getVariable("rentListing");
        User user = (User) execution.getVariable("authorizedUser");
        rentStrategy.addListing(listing, user);
        String response = listing.toString(user);
        execution.setVariable("rentListingResponse", response);
        execution.setVariable("notification", "Listing added successfully!");
    }

    private void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
    }
}
