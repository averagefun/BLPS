package ru.ifmo.blps.model.builders;

import ru.ifmo.blps.model.Apartment;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.SaleListing;

public class RentListingBuilder {
    private String description;
    private String city;
    private String street;
    private Integer house;
    private Integer building;
    private Float area;
    private Integer rooms;
    private Integer price;
    private Integer minDuration;

    public RentListingBuilder description(String description) {
        this.description = description;
        return this;
    }

    public RentListingBuilder city(String city) {
        this.city = city;
        return this;
    }

    public RentListingBuilder street(String street) {
        this.street = street;
        return this;
    }

    public RentListingBuilder house(Integer house) {
        this.house = house;
        return this;
    }

    public RentListingBuilder building(Integer building) {
        this.building = building;
        return this;
    }

    public RentListingBuilder area(Float area) {
        this.area = area;
        return this;
    }

    public RentListingBuilder rooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    public RentListingBuilder price(Integer price) {
        this.price = price;
        return this;
    }

    public RentListingBuilder minDuration(Integer minDuration) {
        this.minDuration = minDuration;
        return this;
    }

    public RentListing build() {
        Apartment apartment = new Apartment(city, street, house, area, rooms);
        apartment.setBuilding(building);
        return new RentListing(description, apartment, price, minDuration);
    }
}



