package ru.ifmo.blps.service;

import java.util.List;
import java.util.Optional;

import org.openapitools.model.Filter;
import org.springframework.stereotype.Service;
import ru.ifmo.blps.model.ListingSpecification;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.repository.RentListingsRepository;
import ru.ifmo.blps.repository.SaleListingsRepository;

@Service
public class ListingsService {
    private final SaleListingsRepository saleListingsRepository;
    private final RentListingsRepository rentListingsRepository;


    public ListingsService(SaleListingsRepository saleListingsRepository, RentListingsRepository rentListingsRepository) {
        this.saleListingsRepository = saleListingsRepository;
        this.rentListingsRepository = rentListingsRepository;
    }

    public void saveSaleListing(SaleListing saleListing) {
        saleListingsRepository.save(saleListing);
    }

    public List<SaleListing> getAllSalesListing() {
        return saleListingsRepository.findAll();
    }

    public void deleteSaleListingIfExist(ListingStatus status) {
        saleListingsRepository.deleteAllByStatus(status);
    }

    public Optional<SaleListing> getCreatedSaleListing() {
        return saleListingsRepository.findFirstByStatus(ListingStatus.CREATED);
    }

    public Integer countSaleListings() {
        return saleListingsRepository.findByStatus(ListingStatus.LISTED).size();
    }

    public List<SaleListing> getSaleListingsByFilter(Filter filter) {
        return saleListingsRepository.findAll(ListingSpecification.findByFilter(filter));
    }

    public Optional<RentListing> getCreatedRentListing() {
        return rentListingsRepository.findFirstByStatus(ListingStatus.CREATED);
    }

    public Optional<RentListing> getVerifiedRentListing() {
        return rentListingsRepository.findFirstByStatus(ListingStatus.VERIFY);
    }

    public void deleteRentListing(RentListing rentListing) {
        rentListingsRepository.delete(rentListing);
    }

    public Optional<SaleListing> getVerifiedSaleListing() {
        return saleListingsRepository.findFirstByStatus(ListingStatus.VERIFY);
    }

    public void deleteSaleListing(SaleListing saleListing) {
        saleListingsRepository.delete(saleListing);
    }

    public int countRentListings(User user) {
        return rentListingsRepository.findByStatus(ListingStatus.LISTED, user.getId()).size();
    }

    public void saveRentListing(RentListing rentListing) {
        rentListingsRepository.save(rentListing);
    }

    public void deleteRentListingIfExist(ListingStatus status, User user) {
        rentListingsRepository.deleteAllByStatus(status, user.getId());
    }

    public List<RentListing> getAllRentsListing() {
        return rentListingsRepository.findAll();
    }

    public List<RentListing> getRentListingsByFilter(Filter filter) {
        return rentListingsRepository.findAll(ListingSpecification.findByFilter(filter));
    }

}
