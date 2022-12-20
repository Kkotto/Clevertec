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
import com.kkotto.Clevertec.service.util.Constants;
import com.kkotto.Clevertec.service.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        LocalDateTime creationTime = LocalDateTime.now();
        CardDto cardDto = generateCard(paymentDto);
        ReceiptDto receiptDto = generateReceipt(paymentDto, creationTime, cardDto.getDiscount());
        List<String> dataForFile = generateLinesForFile(cardDto, receiptDto);
        writeReceiptToFile(creationTime.format(Constants.DATE_TIME_FORMATTER), dataForFile);
        return receiptDto;
    }

    private ReceiptDto generateReceipt(PaymentDto paymentDto, LocalDateTime creationTime, double cardDiscount) {
        List<ProductDto> productsDto = getProductDtoList(paymentDto);
        BigDecimal taxableTotal = countTaxableTotal(productsDto);
        taxableTotal = taxableTotal.multiply(BigDecimal.valueOf(cardDiscount));
        BigDecimal vatAmount = taxableTotal.multiply(BigDecimal.valueOf(Constants.VAT_VALUE));
        return ReceiptDto.builder()
                .payDate(creationTime.toLocalDate())
                .payTime(creationTime.toLocalTime())
                .products(productsDto)
                .taxableTotal(taxableTotal)
                .vatAmount(vatAmount)
                .totalForPayment(vatAmount.add(taxableTotal))
                .build();
    }

    private CardDto generateCard(PaymentDto paymentDto) {
        Card card = cardRepository.findCardByCardLastDigits(paymentDto.getCardNumber());
        return CardDto.builder()
                .ownerName(card.getOwnerName())
                .cardLastDigits(card.getCardLastDigits())
                .discount(card.getDiscount())
                .build();
    }

    private List<ProductDto> getProductDtoList(PaymentDto paymentDto) {
        List<ProductPaymentDto> productPaymentDto = paymentDto.getProducts();
        return productPaymentDto.stream()
                .map(this::convertToProductDto)
                .toList();
    }

    private ProductDto convertToProductDto(ProductPaymentDto productPaymentDto) {
        Product product = productRepository.findById(productPaymentDto.getProductId()).get();
        String name = product.getName();
        BigDecimal price = product.getPrice();
        Integer quantity = productPaymentDto.getQuantity();
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        boolean isDiscount = false;
        if (productPaymentDto.getQuantity() > Constants.PRODUCT_AMOUNT_FOR_DISCOUNT) {
            totalPrice = totalPrice.multiply(BigDecimal.valueOf(Constants.FULL_PRICE_VALUE - Constants.DISCOUNT_VALUE));
            isDiscount = true;
        }
        return new ProductDto.Builder()
                .name(name)
                .price(price)
                .quantity(productPaymentDto.getQuantity())
                .totalPrice(totalPrice)
                .isDiscount(isDiscount)
                .build();
    }

    private BigDecimal countTaxableTotal(List<ProductDto> productDto) {
        BigDecimal result = new BigDecimal("0");
        for (ProductDto product : productDto) {
            result = result.add(product.getTotalPrice());
        }
        return result;
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
        lines.add("---------------");
        lines.add("\n");
        lines.add("QTY DESCRIPTION PRICE TOTAL\n");
        List<ProductDto> products = receiptDto.getProducts();
        for (ProductDto product : products) {
            lines.add(product.toString() + "\n");
            if (product.isDiscount()) {
                lines.add("Discount: " + Constants.DISCOUNT_VALUE + "\n");
            }
            lines.add("\n");
        }
        lines.add("---------------");
        lines.add("\n");
        lines.add(cardDto.toString() + "\n");
        lines.add("\n");
        lines.add("---------------");
        lines.add("\n");
        lines.add("TAXABLE TOT. - " + receiptDto.getTaxableTotal() + "\n");
        lines.add("VAT17% - " + receiptDto.getVatAmount() + "\n");
        lines.add("TOTAL - " + receiptDto.getTotalForPayment() + "\n");
        return lines;
    }
}
