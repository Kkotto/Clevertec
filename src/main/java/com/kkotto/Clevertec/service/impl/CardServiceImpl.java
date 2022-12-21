package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.service.CardService;
import com.kkotto.Clevertec.service.model.entity.Card;
import com.kkotto.Clevertec.service.model.response.CardDto;
import com.kkotto.Clevertec.service.util.FileUtil;
import com.kkotto.Clevertec.service.util.consts.CardResponse;
import com.kkotto.Clevertec.service.util.consts.Constants;
import com.kkotto.Clevertec.service.util.consts.ConstantsResponses;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    static final Logger logger = LogManager.getLogger(CardServiceImpl.class);

    @Override
    public ResponseEntity<String> createCard(CardDto cardDto) {
        if (cardDto == null || cardDto.getCardLastDigits() == null) {
            return new ResponseEntity<>(ConstantsResponses.CARD_IS_NULL_RESPONSE, HttpStatus.BAD_REQUEST);
        }
        switch (isCardSaved(cardDto)) {
            case ENTITY_IS_SAVED -> {
                return new ResponseEntity<>(ConstantsResponses.CARD_IS_SAVED_RESPONSE, HttpStatus.OK);
            }
            case ENTITY_ALREADY_EXISTS -> {
                return new ResponseEntity<>(ConstantsResponses.CARD_ALREADY_EXISTS_RESPONSE, HttpStatus.BAD_REQUEST);
            }
            default -> {
                return new ResponseEntity<>(ConstantsResponses.CARD_IS_NULL_RESPONSE, HttpStatus.BAD_REQUEST);
            }
        }
    }

    private CardResponse isCardSaved(CardDto cardDto) {
        Card card = new Card();
        try {
            String name = cardDto.getOwnerName();
            Double discount = cardDto.getDiscount();
            Integer lastDigits = cardDto.getCardLastDigits();
            if (name != null) {
                card.setOwnerName(name);
            }
            if (discount != null) {
                card.setDiscount(discount);
            }
            if (lastDigits != null) {
                card.setCardLastDigits(lastDigits);
            }
            if (!isCardExist(lastDigits)) {
                cardRepository.save(card);
                return CardResponse.ENTITY_IS_SAVED;
            } else {
                return CardResponse.ENTITY_ALREADY_EXISTS;
            }
        } catch (NumberFormatException exception) {
            logger.error("Number format exception.");
            exception.printStackTrace();
        }
        return CardResponse.INVALID_DATA;
    }

    private boolean isCardExist(Integer lastDigits) {
        return cardRepository.findCardByCardLastDigits(lastDigits) != null;
    }
}
