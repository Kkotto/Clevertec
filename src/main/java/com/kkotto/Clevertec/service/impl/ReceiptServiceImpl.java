package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.ReceiptService;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.request.ProductPaymentDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public ReceiptDto createReceipt(PaymentDto paymentDto) {
        List<ProductPaymentDto> productPaymentDto = paymentDto.getProducts();
        List<ProductDto> productsDto = productPaymentDto.stream()
                .map(this::convertToItemDto)
                .toList();
        BigDecimal taxableTotal = countTaxableTotal(productsDto);
        double vatValue = 0.17;
        BigDecimal vatAmount = taxableTotal.multiply(BigDecimal.valueOf(vatValue));
        return ReceiptDto.builder()
                .payDate(LocalDate.now())
                .payTime(LocalTime.now())
                .shopId(paymentDto.getShopId())
                .cashierId(paymentDto.getCashierId())
                .products(productsDto)
                .taxableTotal(taxableTotal)
                .vatAmount(vatAmount)
                .totalForPayment(vatAmount.add(taxableTotal))
                .build();
    }

    private ProductDto convertToItemDto(ProductPaymentDto productPaymentDto) {
        Product product = productRepository.findById(productPaymentDto.getProductId()).get();
        return new ProductDto.Builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(productPaymentDto.getQuantity())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(productPaymentDto.getQuantity())))
                .build();
    }

    private BigDecimal countTaxableTotal(List<ProductDto> productDto){
        return BigDecimal.valueOf(productDto.stream()
                .map(ProductDto::getTotalPrice)
                .count());
    }
}
