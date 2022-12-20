package com.kkotto.Clevertec.service.model.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductDto {
    private ProductDto() {
    }

    private Integer quantity;
    private String name;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public String toString() {
        return String.format("%d %s $%f $%f", quantity, name, price, totalPrice);
    }

    public static class Builder {
        private ProductDto product;

        public Builder() {
            product = new ProductDto();
        }

        public Builder quantity(Integer quantity) {
            product.quantity = quantity;
            return this;
        }

        public Builder name(String name) {
            product.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            product.price = price;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            product.totalPrice = totalPrice;
            return this;
        }

        public ProductDto build() {
            return product;
        }
    }
}
