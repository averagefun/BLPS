package ru.ifmo.blps.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.model.enums.SellerType;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "status", nullable = false)
    private ListingStatus status;

    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "sellerType")
    private SellerType sellerType;

    public Listing(String description, Apartment apartment, Integer price, ListingStatus status, LocalDateTime createdTime) {
        this.description = description;
        this.apartment = apartment;
        this.price = price;
        this.status = status;
        this.createdTime = createdTime;
    }

    public Listing() {
    }
}
