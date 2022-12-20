package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;

public interface ReceiptService {
    ReceiptDto createReceipt(PaymentDto paymentDto);
}
