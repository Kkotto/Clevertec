package com.kkotto.Clevertec.service.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CashReceiptDto {
    private BigDecimal shopId;
    private Integer cashierId;
    private List<ItemDto> items;
    private LocalDate payDate;
    private LocalTime payTime;
    private BigDecimal taxableTotal;
    private BigDecimal vatAmount;
    private BigDecimal totalForPayment;
}
