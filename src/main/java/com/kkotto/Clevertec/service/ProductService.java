package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;

public interface ProductService {
    ReceiptDto createReceipt(PaymentDto paymentDto);
    ProductDto createProduct(ProductDto productDto);
}
