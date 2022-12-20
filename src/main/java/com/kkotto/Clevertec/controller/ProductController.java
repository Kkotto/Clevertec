package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ProductService;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    //TODO
    //1. создать чек, вывести, сохранить в бд
    //2. вывод чека в файл
    //3. сохранение товаров в файл
    //4. чтение товаров из файла
    @PostMapping("/create-receipt")
    public ReceiptDto createReceipt(@RequestBody PaymentDto paymentDto) {
        return productService.createReceipt(paymentDto);
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }
}