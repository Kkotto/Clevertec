package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.ReceiptService;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.request.ProductPaymentDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import com.kkotto.Clevertec.service.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final ProductRepository productRepository;
    private final FileUtil fileUtil = FileUtil.getInstance();

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
        ReceiptDto receiptDto = ReceiptDto.builder()
                .payDate(LocalDate.now())
                .payTime(LocalTime.now())
                .shopId(paymentDto.getShopId())
                .cashierId(paymentDto.getCashierId())
                .products(productsDto)
                .taxableTotal(taxableTotal)
                .vatAmount(vatAmount)
                .totalForPayment(vatAmount.add(taxableTotal))
                .build();
        writeReceiptToFile(receiptDto);
        return receiptDto;
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

    private BigDecimal countTaxableTotal(List<ProductDto> productDto) {
        return BigDecimal.valueOf(productDto.stream()
                .map(ProductDto::getTotalPrice)
                .count());
    }

    private void writeReceiptToFile(ReceiptDto receiptDto) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String fileName = "Receipts\\Receipt " + LocalDateTime.now().format(format) + ".csv";
        File file = new File(fileName);
        List<String> dataForFile = generateLinesForFile(receiptDto);
        fileUtil.writeToFile(file, dataForFile);
    }

    private List<String> generateLinesForFile(ReceiptDto receiptDto) {
        List<String> lines = new ArrayList<>();
        lines.add("QTY;DESCRIPTION;PRICE;TOTAL");
        List<ProductDto> productsDto = receiptDto.getProducts();
        for (ProductDto product : productsDto) {
            lines.add(product.toString());
        }
        return lines;
    }
}
