package com.kkotto.Clevertec.service.util.consts;

import org.springframework.http.HttpStatus;

public class ConstantsResponses {
    public static final String INVALID_DATA_RESPONSE_MSG = "Invalid: entity has invalid data.";
    public static final HttpStatus INVALID_DATA_RESPONSE = HttpStatus.BAD_REQUEST;
    public static final String SUCCESSFULLY_SAVED_RESPONSE_MSG = "Success: entity was successfully saved.";
    public static final HttpStatus SUCCESSFULLY_SAVED_RESPONSE = HttpStatus.OK;
    public static final String ENTITY_ALREADY_EXISTS_RESPONSE_MSG = "Error: such entity already exists.";
    public static final HttpStatus ENTITY_ALREADY_EXISTS_RESPONSE = HttpStatus.BAD_REQUEST;
}
