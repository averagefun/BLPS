package ru.ifmo.blps.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.blps.model.enums.ListingStatus;

import java.time.LocalDateTime;

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

    public RentListing(String description, String city, String street, Integer house, Float area, Integer rooms, Integer price, Integer minDuration) {
        super(description, city, street, house, area, rooms, price, ListingStatus.CREATED, LocalDateTime.now());
        this.minDuration = minDuration;
    }
}
