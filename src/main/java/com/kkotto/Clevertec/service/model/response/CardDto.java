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
    @NotNull
    private String ownerName;
    @Min(0)
    @Max(1)
    @NotNull
    private double discount;
    @Min(1000)
    @Max(9999)
    @NotNull
    private Integer cardLastDigits;

    public String toString() {
        return String.format("Card owner: %s\nDiscount: %.2f%%\nLast digits: %d", ownerName, discount, cardLastDigits);
    }
}
