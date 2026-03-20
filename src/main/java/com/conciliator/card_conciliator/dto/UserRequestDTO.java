package com.conciliator.card_conciliator.dto;

public record UserRequestDTO(
        String email,
        String password,
        Long enterpriseId
) {}
