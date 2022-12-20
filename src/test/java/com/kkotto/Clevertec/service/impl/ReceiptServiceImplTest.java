package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.request.ProductPaymentDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {
    @Autowired
    ReceiptServiceImpl receiptService;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    CardRepository cardRepository;

    @Test
    void isProductCreatedWhenAlright(){
        ProductPaymentDto product1 = new ProductPaymentDto();
        product1.setProductId(1);
        product1.setQuantity(1);
        List<ProductPaymentDto> products = List.of(product1);
        PaymentDto paymentDtoTest = new PaymentDto();
        paymentDtoTest.setProducts(products);
        paymentDtoTest.setCardNumber(1234);

        ProductDto productDto = new ProductDto.Builder()
                .name("Test-name")
                .quantity(product1.getQuantity())
                .price(BigDecimal.valueOf(12))
                .build();
        List<ProductDto> dtos = List.of(productDto);
        ReceiptDto receiptDto = ReceiptDto.builder()
                .products(dtos)
                .taxableTotal(productDto.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity())))
                .build();
        Card card = new Card();
        card.setDiscount(1);
        card.setOwnerName("Test owner");
        card.setId(1);
        card.setCardLastDigits(1234);
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(12));
        product.setName("name");
        product.setId(1);
        Mockito.when(cardRepository.findCardByCardLastDigits(1234)).thenReturn(card);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        ReceiptDto receipt = receiptService.createReceipt(paymentDtoTest);
        Assertions.assertEquals(BigDecimal.valueOf(12), receiptDto.getTaxableTotal());
    }

}