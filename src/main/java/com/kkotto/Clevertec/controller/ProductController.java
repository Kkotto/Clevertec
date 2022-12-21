package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import com.kkotto.Clevertec.service.util.FileUtil;
import com.kkotto.Clevertec.service.util.consts.ConstantsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final FileUtil fileUtil = FileUtil.getInstance();

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Validated ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam Integer productId) {
        return productService.deleteProduct(productId);
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto productDto, @RequestParam Integer productId) {
        return productService.updateProduct(productDto, productId);
    }

    @GetMapping("/get-product-list")
    public @ResponseBody File getProductList() {
        return productService.getProductList();
    }

    @PostMapping("/read-product-list")
    public ResponseEntity<String> readProductList(@RequestPart MultipartFile file) {
        if (!fileUtil.isFileCSV(file.getOriginalFilename())) {
            return new ResponseEntity<>(ConstantsResponses.WRONG_FILE_EXTENSION_RESPONSE_MSG, ConstantsResponses.WRONG_FILE_EXTENSION_RESPONSE);
        }
        return productService.readProductList(file);
    }
}