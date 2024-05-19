package ru.ifmo.main.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.main.model.enums.ListingStatus;


@Getter
@Setter
@Entity
@Table(name = "saleListings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SaleListing extends Listing {
    public SaleListing() {
    }

    public SaleListing(String description, Apartment apartment, Integer price) {
        super(description, apartment, price, ListingStatus.CREATED, LocalDateTime.now());
    }
}
