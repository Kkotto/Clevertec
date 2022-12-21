package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.response.ProductDto;

import java.io.File;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    void deleteProduct(Integer productId);
    ProductDto updateProduct(ProductDto productDto, Integer productId);
    File getProductList();
}
