package ru.ifmo.blps.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.enums.ListingStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentListingsRepository extends JpaRepository<RentListing, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM RentListing r WHERE r.status = :status")
    void deleteAllByStatus(ListingStatus status);


    Optional<RentListing> findFirstByStatus(ListingStatus status);

    @Query("SELECT r FROM RentListing r WHERE r.status = :status")
    List<RentListing> findByStatus(@Param("status") ListingStatus status);

    List<RentListing> findAll(Specification<Listing> byFilter);
}
