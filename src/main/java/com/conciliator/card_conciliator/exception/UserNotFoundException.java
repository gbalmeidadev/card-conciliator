package com.conciliator.card_conciliator.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("Usuário não encontrado com ID: " + id);
    }
}
