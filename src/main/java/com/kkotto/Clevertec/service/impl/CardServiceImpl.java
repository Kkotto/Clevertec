package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.service.CardService;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.entity.Product;
import com.kkotto.Clevertec.service.model.response.CardDto;
import com.kkotto.Clevertec.service.model.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    @Override
    public CardDto createCard(CardDto cardDto){
        Card card = new Card();
        card.setOwnerName(cardDto.getOwnerName());
        card.setDiscount(cardDto.getDiscount());
        card.setCardLastDigits(cardDto.getCardLastDigits());
        Card save = cardRepository.save(card);
        return CardDto.builder()
                .ownerName(save.getOwnerName())
                .discount(save.getDiscount())
                .cardLastDigits(save.getCardLastDigits()).build();
    }
}
