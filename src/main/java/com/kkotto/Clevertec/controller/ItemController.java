package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.ItemService;
import com.kkotto.Clevertec.service.model.entity.Item;
import com.kkotto.Clevertec.service.model.request.PayDto;
import com.kkotto.Clevertec.service.model.response.CashReceiptDto;
import com.kkotto.Clevertec.service.model.response.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;
    //TODO
    //1. создать чек, вывести, сохранить в бд
    //2. вывод чека в файл
    //3. сохранение товаров в файл
    //4. чтение товаров из файла
    @PostMapping("/create-cash")
    public CashReceiptDto createCashReceipt(@RequestBody PayDto payDto) {
        return itemService.createCashReceipt(payDto);
    }

    @PostMapping("/create")
    public ItemDto createItem(@RequestBody ItemDto itemDto){
        return itemService.createItem(itemDto);
    }
}