package ru.ifmo.blps.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.blps.model.enums.ListingStatus;

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

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house", nullable = false)
    private Integer house;

    @Column(name = "building")
    private Integer building;

    @Column(name = "area", nullable = false)
    private Float area;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "status", nullable = false)
    private ListingStatus status;

    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    public Listing(String description, String city, String street, Integer house, Float area, Integer rooms, Integer price, ListingStatus status, LocalDateTime createdTime) {
        this.description = description;
        this.city = city;
        this.street = street;
        this.house = house;
        this.area = area;
        this.rooms = rooms;
        this.price = price;
        this.status = status;
        this.createdTime = createdTime;
    }


    public Listing() {
    }
}
