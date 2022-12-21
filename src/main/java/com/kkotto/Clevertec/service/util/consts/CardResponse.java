package com.kkotto.Clevertec.service.util.consts;

public enum CardResponse {
    INVALID_DATA("Invalid data."),
    ENTITY_IS_SAVED("Entity was successfully saved."),
    ENTITY_ALREADY_EXISTS("Such entity already exists.");
    final String responseMessage;

    CardResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    int getNumberOfResponse(CardResponse cardResponse) {
        return cardResponse.ordinal();
    }
}
