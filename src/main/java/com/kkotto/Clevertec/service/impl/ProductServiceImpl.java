package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.ProductRepository;
import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.util.Constants;
import com.kkotto.Clevertec.service.util.FileUtil;
import com.kkotto.Clevertec.service.util.IdNotFoundException;
import com.kkotto.Clevertec.service.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileUtil fileUtil = FileUtil.getInstance();
    private final DateTimeUtil dateTimeUtil = DateTimeUtil.getInstance();

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        Product save = productRepository.save(product);
        return convertToProductDto(save);
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(IdNotFoundException::new);
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        Product save = productRepository.save(product);
        return convertToProductDto(save);
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto.Builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    @Override
    public File getProductList() {
        List<Product> productList = productRepository.findAll();
        List<String> productLines = new ArrayList<>();
        productLines.add(Constants.PRODUCT_LIST_FORMAT_HEADER);
        for (Product product : productList) {
            productLines.add(String.format(Constants.PRODUCT_LIST_FORMAT_TEMPLATE, product.getId(), product.getName(), product.getPrice(), dateTimeUtil.formatDateTime(product.getCreateAt())));
        }
        File file = new File(String.format(Constants.PRODUCT_LIST_FORMAT_FILENAME, dateTimeUtil.getFormattedCurrentDateTime()));
        fileUtil.writeToFile(file, productLines);
        return file;
    }

    @Override
    public List<Product> readProductList(MultipartFile file) {
        List<Product> productListToSend = new ArrayList<>();
        try {
            byte[] byteText = file.getBytes();
            String text = new String(byteText, StandardCharsets.UTF_8);
            String[] textLines = text.split(Constants.EMPTY_LINE);
            List<Product> productList = new ArrayList<>();
            for (String line : textLines) {
                if (line.matches(Constants.PRODUCT_LIST_REGEX_TEMPLATE)) {
                    String[] params = line.split(Constants.PRODUCT_PARAMS_SEPARATOR_REGEX);
                    Product product = new Product();
                    product.setName(params[1]);
                    String price = params[2];
                    if (price.contains(",")) {
                        price = price.replace(",", ".");
                    }
                    product.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
                    product.setCreateAt(dateTimeUtil.convertToDateTime(params[3]));
                    productList.add(product);
                }
            }
            for (Product product : productList) {
                productListToSend.add(saveProduct(product));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productListToSend;
    }
}
