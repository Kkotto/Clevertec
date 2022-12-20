package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.ReceiptService;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.request.ProductPaymentDto;
import com.kkotto.Clevertec.service.model.response.CardDto;
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
    private final CardRepository cardRepository;
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime creationTime = LocalDateTime.now();
        ReceiptDto receiptDto = ReceiptDto.builder()
                .payDate(creationTime.toLocalDate())
                .payTime(creationTime.toLocalTime())
                .products(productsDto)
                .taxableTotal(taxableTotal)
                .vatAmount(vatAmount)
                .totalForPayment(vatAmount.add(taxableTotal))
                .build();
        Card card = cardRepository.findCardByCardLastDigits(paymentDto.getCardNumber());
        CardDto cardDto = CardDto.builder()
                .ownerName(card.getOwnerName())
                .cardLastDigits(card.getCardLastDigits())
                .discount(card.getDiscount())
                .build();
        List<String> dataForFile = generateLinesForFile(cardDto, receiptDto);
        writeReceiptToFile(creationTime.format(format), dataForFile);
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

    private void writeReceiptToFile(String creationTime, List<String> dataForFile) {
        String fileName = "Receipts\\Receipt " + creationTime + ".txt";
        File file = new File(fileName);
        fileUtil.writeToFile(file, dataForFile);
    }

    private List<String> generateLinesForFile(CardDto cardDto, ReceiptDto receiptDto) {
        List<String> lines = new ArrayList<>();
        lines.add("CASH RECEIPT\n");
        lines.add("DATE: " + receiptDto.getPayDate() + "\n");
        lines.add("TIME: " + receiptDto.getPayTime() + "\n");
        lines.add("\n");
        lines.add("QTY DESCRIPTION PRICE TOTAL\n");
        List<ProductDto> products = receiptDto.getProducts();
        for (ProductDto product : products) {
            lines.add(product.toString() + "\n");
        }
        lines.add("\n");
        lines.add(cardDto.toString() + "\n");
        lines.add("TAXABLE TOT. - " + receiptDto.getTaxableTotal() + "\n");
        lines.add("VAT17% - " + receiptDto.getVatAmount() + "\n");
        lines.add("TOTAL - " + receiptDto.getTotalForPayment() + "\n");
        return lines;
    }
}
