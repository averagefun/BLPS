package ru.ifmo.blps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.blps.model.SaleListing;

@Repository
public interface SaleListingsRepository extends JpaRepository<SaleListing, Long> {
}
