package ru.ifmo.blps.utils.convertors;

import org.openapitools.model.SaleListingRequest;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.builders.SaleListingBuilder;

@Component
public class SaleListingConvertor {
    public SaleListing dto2Model(SaleListingRequest dto){
        return new SaleListingBuilder()
                .city(dto.getCity())
                .street(dto.getCity())
                .house(dto.getHouse())
                .building(dto.getBuilding())
                .rooms(dto.getRooms())
                .area(dto.getArea())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}
