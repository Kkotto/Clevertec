package com.kkotto.Clevertec.service.util;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(){
        super("Объект с таким id не найден");
    }
}
