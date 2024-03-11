package ru.ifmo.blps.service;

import org.springframework.stereotype.Service;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.SaleListing;
import ru.ifmo.blps.repository.RentListingsRepository;
import ru.ifmo.blps.repository.SaleListingsRepository;

import java.util.List;

@Service
public class ListingsService {
    private final SaleListingsRepository saleListingsRepository;
    private final RentListingsRepository rentListingsRepository;


    public ListingsService(SaleListingsRepository saleListingsRepository, RentListingsRepository rentListingsRepository) {
        this.saleListingsRepository = saleListingsRepository;
        this.rentListingsRepository = rentListingsRepository;
    }

    public void saveSaleListing(SaleListing saleListing){
        saleListingsRepository.save(saleListing);
    }

    public List<SaleListing> getAllSalesListing(){
        return saleListingsRepository.findAll();
    }

    public void saveRentListing(RentListing rentListing){
        rentListingsRepository.save(rentListing);
    }

    public List<RentListing> getAllRentsListing(){
        return rentListingsRepository.findAll();
    }
}
