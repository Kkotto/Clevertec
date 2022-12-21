package com.kkotto.Clevertec.service.model.response;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
public class ProductDto {
    private ProductDto() {
    }

    @Min(1)
    private Integer quantity;
    @NotNull
    private String name;
    @Min(0)
    private BigDecimal price;
    private BigDecimal totalPrice;
    private boolean isDiscount;

    public String toString() {
        return String.format("%d %s $%.2f $%.2f", quantity, name, price, totalPrice);
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

        public Builder isDiscount(boolean isDiscount) {
            product.isDiscount = isDiscount;
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
