package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.CardService;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import com.kkotto.Clevertec.service.model.response.CardDto;
import com.kkotto.Clevertec.service.model.response.ReceiptDto;
import liquibase.pro.packaged.C;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    @PostMapping("/create")
    public CardDto createReceipt(@RequestBody CardDto cardDto) {
        return cardService.createCard(cardDto);
    }
}
