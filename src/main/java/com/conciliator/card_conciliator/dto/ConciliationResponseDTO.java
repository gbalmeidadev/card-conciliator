package com.conciliator.card_conciliator.dto;

import com.conciliator.card_conciliator.entity.DivergenceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConciliationResponseDTO (

        Long id,
        Long enterpriseId,

        Long saleId,
        String saleCode,
        String nsu,

        Long transactionId,
        String transactionCode,

        BigDecimal expectedAmount,
        BigDecimal receivedAmount,
        BigDecimal differenceAmount,

        DivergenceType divergenceType,

        String cardBrand,
        String transactionType,

        LocalDateTime conciliationDate

) {}
