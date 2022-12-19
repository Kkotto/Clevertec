package com.kkotto.Clevertec.service.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createAt;

    @PrePersist
    private void prePersist() {
        createAt = LocalDateTime.now();
    }
}

