package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Validated ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Integer productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @RequestParam Integer productId) {
        return productService.updateProduct(productDto, productId);
    }

    @GetMapping("/get-product-list")
    public @ResponseBody File getProductList() {
        return productService.getProductList();
    }

    @PostMapping("/read-product-list")
    public List<Product> readProductList(@RequestPart MultipartFile file) {
        return productService.readProductList(file);
    }
}