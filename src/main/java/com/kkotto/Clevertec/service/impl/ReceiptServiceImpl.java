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
import com.kkotto.Clevertec.service.util.consts.Constants;
import com.kkotto.Clevertec.service.util.consts.ConstantsReceiptTemplate;
import com.kkotto.Clevertec.service.util.FileUtil;
import com.kkotto.Clevertec.service.util.DateTimeUtil;
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
    private final DateTimeUtil dateTimeUtil = DateTimeUtil.getInstance();

    @Transactional
    @Override
    public ReceiptDto createReceipt(PaymentDto paymentDto) {
        LocalDateTime creationTime = dateTimeUtil.getCurrentDateTime();
        CardDto cardDto = getCardDto(paymentDto);
        double cardDiscount = getCardDiscount(cardDto);
        ReceiptDto receiptDto = createReceiptDto(paymentDto, creationTime, cardDiscount);
        if (receiptDto == null) {
            return null;
        }
        List<String> dataForFile = generateLinesForFile(cardDto, receiptDto);
        writeReceiptToFile(dateTimeUtil.formatDateTime(creationTime), dataForFile);
        return receiptDto;
    }

    private ReceiptDto createReceiptDto(PaymentDto paymentDto, LocalDateTime creationTime, double cardDiscount) {
        List<ProductDto> productsDto = getProductDtoList(paymentDto);
        if (productsDto == null) {
            return null;
        }
        BigDecimal taxableTotal = countTaxableTotal(productsDto);
        taxableTotal = taxableTotal.multiply(BigDecimal.valueOf(Constants.FULL_PRICE_VALUE - cardDiscount));
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

    private double getCardDiscount(CardDto cardDto) {
        if (cardDto == null) {
            return Constants.NO_DISCOUNT_VALUE;
        }
        return cardDto.getDiscount();
    }

    private CardDto getCardDto(PaymentDto paymentDto) {
        if (paymentDto.getCardNumber() == null) {
            return null;
        }
        Card card = cardRepository.findCardByCardLastDigits(paymentDto.getCardNumber());
        if (card == null) {
            return null;
        }
        return CardDto.builder()
                .ownerName(card.getOwnerName())
                .cardLastDigits(card.getCardLastDigits())
                .discount(card.getDiscount())
                .build();
    }

    private List<ProductDto> getProductDtoList(PaymentDto paymentDto) {
        List<ProductPaymentDto> productPaymentDtoList = paymentDto.getProducts();
        if (productPaymentDtoList == null) {
            return null;
        }
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductPaymentDto productPaymentDto : productPaymentDtoList) {
            ProductDto productDto = convertToProductDto(productPaymentDto);
            if (productDto != null) {
                productDtoList.add(productDto);
            }
        }
        return productDtoList;
    }

    private ProductDto convertToProductDto(ProductPaymentDto productPaymentDto) {
        Product product = productRepository.findById(productPaymentDto.getProductId()).get();
        if (product == null) {
            return null;
        }
        String name = product.getName();
        BigDecimal price = product.getPrice();
        Integer quantity = productPaymentDto.getQuantity();
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        boolean isDiscount = false;
        if (productPaymentDto.getQuantity() > Constants.PRODUCT_AMOUNT_FOR_DISCOUNT) {
            totalPrice = totalPrice.multiply(BigDecimal.valueOf(Constants.FULL_PRICE_VALUE - Constants.DISCOUNT_VALUE_FOR_PRODUCT_AMOUNT));
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
        lines.add(ConstantsReceiptTemplate.CASH_RECEIPT_LINE);
        lines.add(String.format(ConstantsReceiptTemplate.DATE_FORMAT_LINE, dateTimeUtil.formatDate(receiptDto.getPayDate())));
        lines.add(String.format(ConstantsReceiptTemplate.TIME_FORMAT_LINE, dateTimeUtil.formatTime(receiptDto.getPayTime())));
        lines.add(ConstantsReceiptTemplate.LINE_SEPARATOR);
        lines.add(ConstantsReceiptTemplate.HEADER_LINE);
        List<ProductDto> products = receiptDto.getProducts();
        for (ProductDto product : products) {
            lines.add(product.toString());
            lines.add(Constants.EMPTY_LINE);
            if (product.isDiscount()) {
                lines.add(String.format(ConstantsReceiptTemplate.DISCOUNT_FORMAT_LINE, Constants.DISCOUNT_VALUE_FOR_PRODUCT_AMOUNT * Constants.DISCOUNT_PERCENT_COEFFICIENT));
                lines.add(Constants.EMPTY_LINE);
            }
        }
        lines.add(ConstantsReceiptTemplate.LINE_SEPARATOR);
        if (cardDto != null) {
            lines.add(cardDto + "\n");
            lines.add(ConstantsReceiptTemplate.LINE_SEPARATOR);
        }
        lines.add(String.format(ConstantsReceiptTemplate.TAXABLE_FORMAT_LINE, receiptDto.getTaxableTotal()));
        lines.add(String.format(ConstantsReceiptTemplate.VAT_FORMAT_LINE, Constants.VAT_VALUE * Constants.DISCOUNT_PERCENT_COEFFICIENT, receiptDto.getVatAmount()));
        lines.add(String.format(ConstantsReceiptTemplate.TOTAL_FORMAT_LINE, receiptDto.getTotalForPayment()));
        return lines;
    }
}
