package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.worker.rent.RentStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetRentListingsDelegate implements JavaDelegate {

    private final RentStrategy rentStrategy;

    @Autowired
    public GetRentListingsDelegate(RentStrategy rentStrategy) {
        this.rentStrategy = rentStrategy;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<RentListing> rentListings = rentStrategy.getAllListings();
        String listingsString = rentListings.stream()
                .map(RentListing::toString) // Assumes RentListing has a meaningful toString() method
                .collect(Collectors.joining("\n"));
        execution.setVariable("listings", listingsString);
    }
}