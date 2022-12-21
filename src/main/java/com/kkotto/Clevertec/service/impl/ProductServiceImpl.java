package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.util.consts.Constants;
import com.kkotto.Clevertec.service.util.FileUtil;
import com.kkotto.Clevertec.service.util.IdNotFoundException;
import com.kkotto.Clevertec.service.util.DateTimeUtil;
import com.kkotto.Clevertec.service.util.consts.ConstantsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileUtil fileUtil = FileUtil.getInstance();
    private final DateTimeUtil dateTimeUtil = DateTimeUtil.getInstance();

    @Override
    public ResponseEntity<String> createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
        return new ResponseEntity<>(ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE_MSG, ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer productId) {
        if (!isProductExists(productId)) {
            return new ResponseEntity<>(ConstantsResponses.ENTITY_DOES_NOT_EXIST_RESPONSE_MSG, ConstantsResponses.ENTITY_DOES_NOT_EXIST_RESPONSE);
        } else {
            productRepository.deleteById(productId);
            return new ResponseEntity<>(ConstantsResponses.ENTITY_SUCCESSFULLY_DELETED_RESPONSE_MSG, ConstantsResponses.ENTITY_SUCCESSFULLY_DELETED_RESPONSE);
        }
    }

    private boolean isProductExists(Integer productId) {
        return productRepository.findById(productId).isPresent();
    }

    @Override
    public ResponseEntity<String> updateProduct(ProductDto productDto, Integer productId) {
        if (!isProductExists(productId)) {
            return new ResponseEntity<>(ConstantsResponses.ENTITY_DOES_NOT_EXIST_RESPONSE_MSG, ConstantsResponses.ENTITY_DOES_NOT_EXIST_RESPONSE);
        }
        Product product = productRepository.findById(productId).orElseThrow(IdNotFoundException::new);
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        productRepository.save(product);
        return new ResponseEntity<>(ConstantsResponses.ENTITY_SUCCESSFULLY_UPDATED_RESPONSE_MSG, ConstantsResponses.ENTITY_SUCCESSFULLY_UPDATED_RESPONSE);
    }

    @Override
    public File getProductList() {
        List<Product> productList = productRepository.findAll();
        List<String> productLines = generateLinesForProductListFile(productList);
        String fileName = String.format(Constants.PRODUCT_LIST_FORMAT_FILENAME, dateTimeUtil.getFormattedCurrentDateTime());
        File file = new File(fileName);
        fileUtil.writeToFile(file, productLines);
        return file;
    }

    private List<String> generateLinesForProductListFile(List<Product> productList) {
        List<String> productLines = new ArrayList<>();
        productLines.add(Constants.PRODUCT_LIST_FORMAT_HEADER);
        for (Product product : productList) {
            String line = String.format(Constants.PRODUCT_LIST_FORMAT_TEMPLATE,
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    dateTimeUtil.formatDateTime(product.getCreateAt()));
            productLines.add(line);
        }
        return productLines;
    }

    @Override
    public ResponseEntity<String> readProductList(MultipartFile file) {
        String[] textLines = convertToStringLines(file);
        for (String line : textLines) {
            if (line.matches(Constants.PRODUCT_LIST_REGEX_TEMPLATE)) {
                String[] params = line.split(Constants.PRODUCT_PARAMS_SEPARATOR_REGEX);
                String name = params[Constants.PARAM_NUMBER_PRODUCT_NAME_FOR_READING_FILE];
                BigDecimal price = convertToBigDecimal(params[Constants.PARAM_NUMBER_PRODUCT_PRICE_FOR_READING_FILE]);
                LocalDateTime createAt = dateTimeUtil.convertToDateTime(params[Constants.PARAM_NUMBER_PRODUCT_CREATE_AT_FOR_READING_FILE]);
                saveProduct(name, price, createAt);
            }
        }
        return new ResponseEntity<>(ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE_MSG, ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE);
    }

    private void saveProduct(String name, BigDecimal price, LocalDateTime createAt) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreateAt(createAt);
        productRepository.save(product);
    }

    private String[] convertToStringLines(MultipartFile file) {
        try {
            byte[] byteText = file.getBytes();
            String text = new String(byteText, StandardCharsets.UTF_8);
            return text.split(Constants.EMPTY_LINE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal convertToBigDecimal(String value) {
        String bigDecimalValue = value;
        if (bigDecimalValue.contains(Constants.BIG_DECIMAL_INCORRECT_SEPARATOR)) {
            bigDecimalValue = bigDecimalValue.replace(Constants.BIG_DECIMAL_INCORRECT_SEPARATOR, Constants.BIG_DECIMAL_CORRECT_SEPARATOR);
        }
        return BigDecimal.valueOf(Double.parseDouble(bigDecimalValue));
    }
}
