package ru.ifmo.blps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.blps.model.RentListing;

@Repository
public interface RentListingsRepository extends JpaRepository<RentListing, Long> {
}
