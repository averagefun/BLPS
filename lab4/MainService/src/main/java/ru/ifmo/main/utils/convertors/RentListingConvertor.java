package ru.ifmo.main.utils.convertors;

import org.openapitools.model.RentListingRequest;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.builders.RentListingBuilder;

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
