package ru.ifmo.blps.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import org.openapitools.model.SellerType;
import ru.ifmo.blps.model.enums.ListingStatus;

@Getter
@Setter
@MappedSuperclass
public abstract class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private long authorId;

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



    public String toString(String author, DateTimeFormatter formatter) {
        return String.format("Объявление от %s: %s\nАпартаменты: %s\nЦена: %s\nВремя создания объявления: %s\nТип продавца: %s",
                author, description, apartment, price, createdTime.format(formatter), sellerType);
    }
}
