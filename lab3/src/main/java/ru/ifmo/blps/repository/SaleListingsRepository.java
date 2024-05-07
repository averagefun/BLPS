package ru.ifmo.blps.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.enums.ListingStatus;


@Repository
public interface SaleListingsRepository extends JpaRepository<SaleListing, Long> {
    @Modifying
    @Query("DELETE FROM SaleListing s WHERE s.status = :status")
    void deleteAllByStatus(ListingStatus status);

    Optional<SaleListing> findFirstByStatus(ListingStatus status);

    @Query("SELECT r FROM SaleListing r WHERE r.status = :status")
    List<SaleListing> findByStatus(@Param("status") ListingStatus status);

    List<SaleListing> findAll(Specification<Listing> byFilter);

    List<SaleListing> findByStatusAndCreatedTimeBetween(ListingStatus status, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
