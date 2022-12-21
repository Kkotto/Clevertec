package com.kkotto.Clevertec.service;

import com.kkotto.Clevertec.service.model.response.CardDto;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<String> createCard(CardDto cardDto);
}
