package ru.ifmo.blps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.enums.ListingStatus;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


@Repository
public interface SaleListingsRepository extends JpaRepository<SaleListing, Long> {


    @Transactional
    @Modifying
    @Query("DELETE FROM SaleListing s WHERE s.status = :status")
    void deleteAllByStatus(ListingStatus status);

    Optional<SaleListing> findFirstByStatus(ListingStatus status);

    @Query("SELECT r FROM SaleListing r WHERE r.status = :status")
    List<SaleListing> findByStatus(@Param("status") ListingStatus status);


}
