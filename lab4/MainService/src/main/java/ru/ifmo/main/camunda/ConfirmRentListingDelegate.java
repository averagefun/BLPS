package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.main.exceptions.NoSuchListingsException;
import ru.ifmo.main.model.User;
import ru.ifmo.main.model.enums.ConformationType;
import ru.ifmo.main.service.UsersService;
import ru.ifmo.main.worker.rent.RentStrategy;

@Component
public class ConfirmRentListingDelegate implements JavaDelegate {

    private final RentStrategy rentStrategy;

    @Autowired
    public ConfirmRentListingDelegate(RentStrategy rentStrategy) {
        this.rentStrategy = rentStrategy;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String conformationType = (String) execution.getVariable("conformationType");

        ConformationType type = ConformationType.fromString(conformationType);
        User user = (User) execution.getVariable("authorizedUser");

        try {
            rentStrategy.confirmListing(type, user);
            execution.setVariable("confirmationStatus", "success");
        } catch (NoSuchListingsException e) {
            execution.setVariable("confirmationStatus", "No such listings found");
        } catch (RuntimeException e) {
            execution.setVariable("confirmationStatus", "Error: " + e.getMessage());
        }
    }
}
