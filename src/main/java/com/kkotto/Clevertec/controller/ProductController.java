package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ProductDto createProduct(@RequestBody @Validated ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Integer productId){
        productService.deleteProduct(productId);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @RequestParam Integer productId){
        return productService.updateProduct(productDto, productId);
    }

}