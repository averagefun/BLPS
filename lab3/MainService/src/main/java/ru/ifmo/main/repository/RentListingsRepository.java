package ru.ifmo.main.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.main.model.Listing;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.enums.ListingStatus;

@Repository
public interface RentListingsRepository extends JpaRepository<RentListing, Long> {

    @Modifying
    @Query("DELETE FROM RentListing r WHERE r.status = :status AND r.authorId = :userId")
    void deleteAllByStatus(ListingStatus status, long userId);

    Optional<RentListing> findFirstByStatus(ListingStatus status);

    @Query("SELECT r FROM RentListing r WHERE r.status = :status AND r.authorId = :userId")
    List<RentListing> findByStatus(@Param("status") ListingStatus status, long userId);

    List<RentListing> findAll(Specification<Listing> byFilter);

    List<RentListing> findByStatusAndCreatedTimeBetween(ListingStatus status, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
