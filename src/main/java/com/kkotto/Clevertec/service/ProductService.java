package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.response.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ProductService {
    ResponseEntity<String> createProduct(ProductDto productDto);
    ResponseEntity<String> deleteProduct(Integer productId);
    ResponseEntity<String> updateProduct(ProductDto productDto, Integer productId);
    File getProductList();
    ResponseEntity<String> readProductList(MultipartFile file);
}
