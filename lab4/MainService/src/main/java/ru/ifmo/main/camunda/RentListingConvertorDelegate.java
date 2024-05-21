package ru.ifmo.main.camunda;

import com.fasterxml.jackson.annotation.JsonValue;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.json.SpinJsonNode;
import org.openapitools.model.RentListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.RentListing;
import ru.ifmo.main.model.builders.RentListingRequestUtils;
import ru.ifmo.main.utils.convertors.RentListingConvertor;

@Component("rentListingConvertorDelegate")
public class RentListingConvertorDelegate implements JavaDelegate {

    private final RentListingConvertor rentListingConvertor;

    @Autowired
    public RentListingConvertorDelegate(RentListingConvertor rentListingConvertor) {
        this.rentListingConvertor = rentListingConvertor;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        checkAuthentication();
        String listingJson = (String) execution.getVariable("listing");
        RentListingRequest request = RentListingRequestUtils.fromJson(listingJson);
        RentListing listing = rentListingConvertor.dto2Model(request);
        execution.setVariable("rentListing", listing);
    }

    private void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
    }
}