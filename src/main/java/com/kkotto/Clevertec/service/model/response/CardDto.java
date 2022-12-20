package com.kkotto.Clevertec.service.model.response;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
public class CardDto {
    private String ownerName;
    @Min(0)
    @Max(1)
    private double discount;
    @NotNull
    @Min(1000)
    @Max(9999)
    private Integer cardLastDigits;

    public String toString() {
        return String.format("Card owner: %s\nDiscount: %f%%\nLast digits: %d", ownerName, discount, cardLastDigits);
    }
}
