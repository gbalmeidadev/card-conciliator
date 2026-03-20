package com.conciliator.card_conciliator.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado com ID: " + id);
    }
}
