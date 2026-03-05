package org.skypro.skyshop.exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String e) {
        super("Не найден продукт ID: " + e);
    }
}
