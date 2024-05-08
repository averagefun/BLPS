package ru.ifmo.main.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Filter;
import org.springframework.stereotype.Service;
import ru.ifmo.main.dto.ListingVerificationMessage;
import ru.ifmo.main.model.Listing;
import ru.ifmo.main.model.ListingSpecification;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.SaleListing;
import ru.ifmo.main.model.User;
import ru.ifmo.main.model.enums.ListingStatus;
import ru.ifmo.main.repository.RentListingsRepository;
import ru.ifmo.main.repository.SaleListingsRepository;

@Service
@Slf4j
public class ListingsService {
    private final SaleListingsRepository saleListingsRepository;
    private final RentListingsRepository rentListingsRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    public ListingsService(SaleListingsRepository saleListingsRepository,
                           RentListingsRepository rentListingsRepository,
                           KafkaProducerService kafkaProducerService,
                           ObjectMapper objectMapper) {
        this.saleListingsRepository = saleListingsRepository;
        this.rentListingsRepository = rentListingsRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = objectMapper;
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

    public Optional<RentListing> getCreatedRentListing(long userId) {
        return rentListingsRepository.findByStatus(ListingStatus.CREATED, userId).stream().findFirst();
    }

    public Optional<RentListing> getVerifiedRentListing(long userId) {
        return rentListingsRepository.findByStatus(ListingStatus.VERIFY, userId).stream().findFirst();
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

    public int countRentListings(long userId) {
        return rentListingsRepository.findByStatus(ListingStatus.LISTED, userId).size();
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

    private List<SaleListing> getSaleListingsByStatusAndToday(ListingStatus status) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        return saleListingsRepository.findByStatusAndCreatedTimeBetween(status, startOfDay, endOfDay);
    }

    public List<RentListing> getRentListingsByStatusAndToday(ListingStatus status) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        return rentListingsRepository.findByStatusAndCreatedTimeBetween(status, startOfDay, endOfDay);
    }


    public List<Listing> findByStatusAndCreatedTime(ListingStatus status) {
        return Stream.concat(getSaleListingsByStatusAndToday(status).stream(),
                        getRentListingsByStatusAndToday(status).stream())
                .toList();
    }

    public void sendListingToVerification(Listing listing, User user) {
        try {
            ListingVerificationMessage listingVerificationMessage = new ListingVerificationMessage(listing);
            String message = objectMapper.writeValueAsString(listingVerificationMessage);
            log.info("Sending message to ListingsVerification {}", message);
            kafkaProducerService.sendMessage("ListingsVerification",
                    listingVerificationMessage.listing().toString(user));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message");
        }
    }
}
