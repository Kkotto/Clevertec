package com.kkotto.Clevertec.service.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PaymentDto {
    private BigDecimal shopId;
    private Integer cashierId;
    private List<ProductPaymentDto> products;
    private Integer cardNumber;

}
