package com.kkotto.Clevertec.service.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentDto {
    private List<ProductPaymentDto> products;
    private Integer cardNumber;

}
