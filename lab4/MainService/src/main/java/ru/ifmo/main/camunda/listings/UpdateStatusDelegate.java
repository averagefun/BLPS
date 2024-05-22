package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.service.ListingsService;
import org.springframework.security.core.GrantedAuthority;

@Component
public class UpdateStatusDelegate implements JavaDelegate {

    private final ListingsService listingsService;

    public UpdateStatusDelegate(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (!isAdmin()) {
            throw new SecurityException("Access denied. Only ADMIN can execute this task.");
        }

        RentListing rentListing = (RentListing) execution.getVariable("rentListing");
        listingsService.saveRentListing(rentListing);
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }
}