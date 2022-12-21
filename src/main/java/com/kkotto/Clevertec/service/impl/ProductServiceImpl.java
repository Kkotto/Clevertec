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

import java.io.File;
import java.util.ArrayList;
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
}
