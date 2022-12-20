package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ReceiptService;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {
    private final ReceiptService receiptService;
    @PostMapping("/create")
    public ReceiptDto createReceipt(@RequestBody PaymentDto paymentDto) {
        return receiptService.createReceipt(paymentDto);
    }
}
