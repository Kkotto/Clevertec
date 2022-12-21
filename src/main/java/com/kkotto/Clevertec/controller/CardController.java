package com.kkotto.Clevertec.controller;

import com.kkotto.Clevertec.service.CardService;
import com.kkotto.Clevertec.service.model.response.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody @Valid CardDto cardDto) {
        return cardService.createCard(cardDto);
    }
}
