package com.kkotto.Clevertec.service.util.consts;

import org.springframework.http.HttpStatus;

public class ConstantsResponses {
    public static final String INVALID_DATA_RESPONSE_MSG = "Invalid: entity has invalid data.";
    public static final HttpStatus INVALID_DATA_RESPONSE = HttpStatus.BAD_REQUEST;
    public static final String SUCCESSFULLY_SAVED_RESPONSE_MSG = "Success: entity was successfully saved.";
    public static final HttpStatus SUCCESSFULLY_SAVED_RESPONSE = HttpStatus.OK;
    public static final String ENTITY_ALREADY_EXISTS_RESPONSE_MSG = "Error: such entity already exists.";
    public static final HttpStatus ENTITY_ALREADY_EXISTS_RESPONSE = HttpStatus.BAD_REQUEST;
    public static final String ENTITY_DOES_NOT_EXIST_RESPONSE_MSG = "Error: such entity does not exist.";
    public static final HttpStatus ENTITY_DOES_NOT_EXIST_RESPONSE = HttpStatus.BAD_REQUEST;
    public static final String ENTITY_SUCCESSFULLY_UPDATED_RESPONSE_MSG = "Success: entity was successfully updated.";
    public static final HttpStatus ENTITY_SUCCESSFULLY_UPDATED_RESPONSE = HttpStatus.OK;
    public static final String ENTITY_SUCCESSFULLY_DELETED_RESPONSE_MSG = "Success: entity was successfully deleted.";
    public static final HttpStatus ENTITY_SUCCESSFULLY_DELETED_RESPONSE = HttpStatus.OK;
    public static final String WRONG_FILE_EXTENSION_RESPONSE_MSG = "Please, use .csv file";
    public static final HttpStatus WRONG_FILE_EXTENSION_RESPONSE = HttpStatus.BAD_REQUEST;
}
