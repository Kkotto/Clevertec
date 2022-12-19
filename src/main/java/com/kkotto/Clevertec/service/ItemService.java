package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.request.PayDto;
import com.kkotto.Clevertec.service.model.response.CashReceiptDto;
import com.kkotto.Clevertec.service.model.response.ItemDto;

public interface ItemService {
    CashReceiptDto createCashReceipt(PayDto payDto);
    ItemDto createItem(ItemDto itemDto);
}
