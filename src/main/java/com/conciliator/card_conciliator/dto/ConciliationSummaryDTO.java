package com.conciliator.card_conciliator.dto;

import com.conciliator.card_conciliator.entity.DivergenceType;

import java.math.BigDecimal;

public record ConciliationSummaryDTO (

        Long id,
        String nsu,
        BigDecimal expectedAmount,
        BigDecimal receivedAmount,
        BigDecimal differenceAmount,
        DivergenceType divergenceType
) {}
