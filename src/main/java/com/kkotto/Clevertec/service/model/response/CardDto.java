package com.kkotto.Clevertec.service.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardDto {
    private String ownerName;
    private double discount;
    private Integer cardLastDigits;

    public String toString() {
        return String.format("Card owner: %s\nDiscount: %d%%\nLast digits: %d", ownerName, discount, cardLastDigits);
    }
}
