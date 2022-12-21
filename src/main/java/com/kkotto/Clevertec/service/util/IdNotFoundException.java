package com.kkotto.Clevertec.service.util;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException() {
        super("Entity with such id is not found.");
    }
}
