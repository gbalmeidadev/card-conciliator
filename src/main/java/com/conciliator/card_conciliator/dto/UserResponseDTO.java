package com.conciliator.card_conciliator.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDTO(
        Long id,
        String email,
        Boolean active,
        LocalDateTime createAt,
        Set<String> perfis
) {}

