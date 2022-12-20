package com.kkotto.Clevertec.repository;

import com.kkotto.Clevertec.service.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findCardByCardLastDigits(Integer cardNumber);
}
