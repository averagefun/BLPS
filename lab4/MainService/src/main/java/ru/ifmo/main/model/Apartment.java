package ru.ifmo.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment implements Serializable {
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

    @Override
    public String toString() {
        return String.format("Квартира расположена в городе %s, по адресу: улица %s, дом %s, строение %s. Площадь квартиры: %s кв. м., количество комнат: %s.",
                city, street, house, building == null? 1: building, area, rooms);
    }
}
