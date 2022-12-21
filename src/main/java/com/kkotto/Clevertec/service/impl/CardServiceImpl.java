package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.service.CardService;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.response.CardDto;
import com.kkotto.Clevertec.service.util.consts.ConstantsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public ResponseEntity<String> createCard(CardDto cardDto) {
        if (cardDto == null || cardDto.getCardLastDigits() == null) {
            return new ResponseEntity<>(ConstantsResponses.INVALID_DATA_RESPONSE_MSG, ConstantsResponses.INVALID_DATA_RESPONSE);
        }
        if (isCardSaved(cardDto)) {
            return new ResponseEntity<>(ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE_MSG, ConstantsResponses.SUCCESSFULLY_SAVED_RESPONSE);
        } else {
            return new ResponseEntity<>(ConstantsResponses.ENTITY_ALREADY_EXISTS_RESPONSE_MSG, ConstantsResponses.ENTITY_ALREADY_EXISTS_RESPONSE);
        }
    }

    private boolean isCardSaved(CardDto cardDto) {
        Card card = new Card();
        Integer lastDigits = cardDto.getCardLastDigits();
        card.setOwnerName(cardDto.getOwnerName());
        card.setDiscount(cardDto.getDiscount());
        card.setCardLastDigits(lastDigits);
        if (!isCardExist(lastDigits)) {
            cardRepository.save(card);
            return true;
        } else {
            return false;
        }
    }

    private boolean isCardExist(Integer lastDigits) {
        return cardRepository.findCardByCardLastDigits(lastDigits) != null;
    }
}
