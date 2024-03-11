package ru.ifmo.blps.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.blps.model.enums.ListingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "saleListings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SaleListing extends Listing{

    public SaleListing() {
    }

    public SaleListing(String description, String city, String street, Integer house, Float area, Integer rooms, Integer price) {
        super(description, city, street, house, area, rooms, price, ListingStatus.CREATED, LocalDateTime.now());
    }

}
