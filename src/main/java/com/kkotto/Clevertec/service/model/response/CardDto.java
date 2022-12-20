package com.kkotto.Clevertec.service.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardDto {
    private String ownerName;
    private Integer discount;
    private Integer cardLastDigits;
}
