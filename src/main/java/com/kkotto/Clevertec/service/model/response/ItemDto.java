package com.kkotto.Clevertec.service.model.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemDto {
    private ItemDto() {
    }

    private Integer quantity;
    private String name;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public static class Builder {
        private ItemDto item;

        public Builder() {
            item = new ItemDto();
        }

        public Builder addQuantity(Integer quantity) {
            item.quantity = quantity;
            return this;
        }

        public Builder addName(String name) {
            item.name = name;
            return this;
        }

        public Builder addPrice(BigDecimal price) {
            item.price = price;
            return this;
        }

        public Builder addTotalPrice(BigDecimal totalPrice) {
            item.totalPrice = totalPrice;
            return this;
        }

        public ItemDto build() {
            return item;
        }
    }
}
