package com.conciliator.card_conciliator.exception;

public class EmailAlreadyExistsException extends  RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("E-mail já cadastrado: " + email);
    }
}
