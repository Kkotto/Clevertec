package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ProductService {
    ResponseEntity<String> createProduct(ProductDto productDto);
    ResponseEntity<String> deleteProduct(Integer productId);
    ProductDto updateProduct(ProductDto productDto, Integer productId);
    File getProductList();
    List<Product> readProductList(MultipartFile file);
}
