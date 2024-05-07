package ru.ifmo.blps.utils.convertors;

import org.openapitools.model.RentListingRequest;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.builders.RentListingBuilder;

@Component
public class RentListingConvertor {
    public RentListing dto2Model(RentListingRequest dto){
        return new RentListingBuilder()
                .city(dto.getCity())
                .street(dto.getCity())
                .house(dto.getHouse())
                .building(dto.getBuilding())
                .rooms(dto.getRooms())
                .area(dto.getArea())
                .price(dto.getPrice())
                .minDuration(dto.getMinDuration())
                .description(dto.getDescription())
                .build();
    }
}
