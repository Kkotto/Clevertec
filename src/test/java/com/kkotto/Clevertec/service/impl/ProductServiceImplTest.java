package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    void isProductCreatedWhenAlright(){
        ProductDto productDtoTest = new ProductDto.Builder()
                .name("Test-name")
                .price(BigDecimal.valueOf(10))
                .build();
        Product product = new Product();
        product.setName(productDtoTest.getName());
        product.setPrice(productDtoTest.getPrice());
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ProductDto product1 = productService.createProduct(productDtoTest);
        Assertions.assertEquals(productDtoTest.getName(), product1.getName());
    }
}