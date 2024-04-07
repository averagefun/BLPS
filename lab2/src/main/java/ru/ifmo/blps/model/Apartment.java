package ru.ifmo.blps.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Apartment() {
    }

    public Apartment(String city, String street, Integer house, Float area, Integer rooms) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.area = area;
        this.rooms = rooms;
    }
}
