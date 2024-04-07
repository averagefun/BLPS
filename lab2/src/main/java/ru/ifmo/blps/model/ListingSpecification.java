package ru.ifmo.blps.model;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Filter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
@Slf4j

public class ListingSpecification {

    public static Specification<Listing> findByFilter(Filter filter) {
        log.info(String.valueOf(filter));
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }
            if (filter.getMinArea() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("apartment").get("area"), filter.getMinArea()));
            }
            if (filter.getMaxArea() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("apartment").get("area"), filter.getMaxArea()));
            }
            if (filter.getRooms() != null && !filter.getRooms().isEmpty()) {
                predicates.add(root.get("apartment").get("rooms").in(filter.getRooms()));
            }
            if (filter.getFloor() != null) {
                predicates.add(criteriaBuilder.equal(root.get("apartment").get("floor"), filter.getFloor()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}