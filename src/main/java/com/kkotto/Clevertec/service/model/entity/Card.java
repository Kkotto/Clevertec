package com.kkotto.Clevertec.service.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ownerName;
    private Integer discount;
    private Integer cardLastDigits;
    private LocalDateTime createAt;

    @PrePersist
    private void prePersist() {
        createAt = LocalDateTime.now();
    }
}
