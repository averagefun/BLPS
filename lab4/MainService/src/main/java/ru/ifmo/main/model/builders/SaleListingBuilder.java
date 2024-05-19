package ru.ifmo.main.model.builders;

import ru.ifmo.main.model.Apartment;
import ru.ifmo.main.model.SaleListing;

public class SaleListingBuilder {
    private String description;
    private String city;
    private String street;
    private Integer house;
    private Integer building;
    private Float area;
    private Integer rooms;
    private Integer price;

    public SaleListingBuilder description(String description) {
        this.description = description;
        return this;
    }

    public SaleListingBuilder city(String city) {
        this.city = city;
        return this;
    }

    public SaleListingBuilder street(String street) {
        this.street = street;
        return this;
    }

    public SaleListingBuilder house(Integer house) {
        this.house = house;
        return this;
    }

    public SaleListingBuilder building(Integer building) {
        this.building = building;
        return this;
    }

    public SaleListingBuilder area(Float area) {
        this.area = area;
        return this;
    }

    public SaleListingBuilder rooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    public SaleListingBuilder price(Integer price) {
        this.price = price;
        return this;
    }

    public SaleListing build() {
        Apartment apartment = new Apartment(city, street, house, area, rooms);
        apartment.setBuilding(building);
        return new SaleListing(description, apartment, price);
    }
}



