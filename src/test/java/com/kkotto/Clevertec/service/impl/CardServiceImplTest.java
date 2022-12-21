package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.response.CardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {
    @Autowired
    CardServiceImpl cardService;

    @MockBean
    CardRepository cardRepository;

    @Test
    void isCardCreatedWhenAlright(){
        CardDto cardDtoTest = CardDto.builder()
                .ownerName("TestName")
                .cardLastDigits(1234)
                .discount(0.5)
                .build();
        Card card = new Card();
        card.setOwnerName(cardDtoTest.getOwnerName());
        card.setCardLastDigits(cardDtoTest.getCardLastDigits());
        card.setDiscount(cardDtoTest.getDiscount());
        Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(card);
        ResponseEntity<String> cardDto = cardService.createCard(cardDtoTest);
        Assertions.assertEquals(cardDto, new ResponseEntity<>(HttpStatus.CREATED));
    }
}