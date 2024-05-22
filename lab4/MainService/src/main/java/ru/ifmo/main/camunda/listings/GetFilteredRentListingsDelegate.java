package ru.ifmo.main.camunda.listings;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openapitools.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.worker.rent.RentStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetFilteredRentListingsDelegate implements JavaDelegate {

    private final RentStrategy rentStrategy;

    @Autowired
    public GetFilteredRentListingsDelegate(RentStrategy rentStrategy) {
        this.rentStrategy = rentStrategy;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Filter filter = new Filter();

        if (execution.hasVariable("minPrice")) {
            Object minPrice = execution.getVariable("minPrice");
            if (minPrice instanceof Integer) {
                filter.setMinPrice((Integer) minPrice);
            } else if (minPrice instanceof Long) {
                filter.setMinPrice(((Long) minPrice).intValue());
            }
        }
        if (execution.hasVariable("maxPrice")) {
            Object maxPrice = execution.getVariable("maxPrice");
            if (maxPrice instanceof Integer) {
                filter.setMaxPrice((Integer) maxPrice);
            } else if (maxPrice instanceof Long) {
                filter.setMinPrice(((Long) maxPrice).intValue());
            }
        }

        if (execution.hasVariable("minArea")) {
            Object minArea = execution.getVariable("minArea");
            if (minArea instanceof Long) {
                filter.setMinArea(((Long) minArea).floatValue());
            } else if (minArea instanceof Integer) {
                filter.setMinArea(((Integer) minArea).floatValue());
            }
        }

        if (execution.hasVariable("maxArea")) {
            Object maxArea = execution.getVariable("maxArea");
            if (maxArea instanceof Long) {
                filter.setMaxArea(((Long) maxArea).floatValue());
            } else if (maxArea instanceof Integer) {
                filter.setMaxArea(((Integer) maxArea).floatValue());
            }
        }

        if (execution.hasVariable("rooms")) {
            String roomsString = (String) execution.getVariable("rooms");
            if (roomsString != null && !roomsString.isEmpty()) {
                List<Integer> rooms = Arrays.stream(roomsString.split(","))
                        .map(String::trim)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                filter.setRooms(rooms);
            }
        }

        if (execution.hasVariable("floor")) {
            Object floor = execution.getVariable("floor");
            if (floor instanceof Integer) {
                filter.setFloor((Integer) floor);
            } else if (floor instanceof Long) {
                filter.setMinPrice(((Long) floor).intValue());
            }
        }

        List<RentListing> rentListings = rentStrategy.getAllListingsByFilter(filter);
        String listingsString = rentListings.stream()
                .map(RentListing::toString)
                .collect(Collectors.joining("\n"));
        execution.setVariable("listingsCount", rentListings.size());
        execution.setVariable("listings", listingsString);
    }
}