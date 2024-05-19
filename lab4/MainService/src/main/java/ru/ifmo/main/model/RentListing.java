package ru.ifmo.main.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.main.model.enums.ListingStatus;

@Getter
@Setter
@Entity
@Table(name = "rentListings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RentListing extends Listing {
    @Column(name = "minDuration", nullable = false)
    private Integer minDuration;

    public RentListing() {
    }

    public RentListing(String description, Apartment apartment, Integer price, Integer minDuration) {
        super(description, apartment, price, ListingStatus.CREATED, LocalDateTime.now());
        this.minDuration = minDuration;
    }
}
