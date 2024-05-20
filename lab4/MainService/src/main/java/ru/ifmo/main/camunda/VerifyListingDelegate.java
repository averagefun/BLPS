package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openapitools.model.SellerType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.enums.ListingStatus;

@Component
public class VerifyListingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (!isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN can execute this task.");
        }

        RentListing rentListing = (RentListing) execution.getVariable("rentListing");
        String type = (String) execution.getVariable("sellerType");

        rentListing.setStatus(ListingStatus.VERIFY);
        execution.setVariable("resultMessage", "Listing checked successfully");
        rentListing.setSellerType(SellerType.valueOf(type));

        execution.setVariable("rentListing", rentListing);

    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }
}