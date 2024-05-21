package ru.ifmo.main.model.builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.model.RentListingRequest;

public class RentListingRequestUtils {

    public static RentListingRequest fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, RentListingRequest.class);
    }
}