package com.conciliator.card_conciliator.dto;

import com.conciliator.card_conciliator.entity.DivergenceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ConciliationFilterDTO (

        Long enterpriseId,
        LocalDate startDate,
        LocalDate endDate,
        String cardBrand,
        String transactionType,
        DivergenceType divergenceType
) {}
