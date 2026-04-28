package com.conciliator.card_conciliator.mapper;

import com.conciliator.card_conciliator.dto.ConciliationResponseDTO;
import com.conciliator.card_conciliator.dto.ConciliationSummaryDTO;
import com.conciliator.card_conciliator.entity.Conciliation;

public class ConciliationMapper {

    public static ConciliationResponseDTO toResponse(Conciliation c) {
        return new ConciliationResponseDTO(
                c.getId(),
                c.getEnterprise().getId(),

                c.getSale().getId(),
                c.getSale().getSaleCode(),
                c.getSale().getNsu(),

                c.getOperationTransaction().getId(),
                c.getOperationTransaction().getTransactionCode(),

                c.getExpectedAmount(),
                c.getReceivedAmount(),
                c.getDifferenceAmount(),

                c.getDivergencyType(),

                c.getSale().getCardBrand(),
                c.getSale().getTransactionType(),

                c.getConciliationDate()
        );
    }

    public static ConciliationSummaryDTO toSummary(Conciliation c) {
        return new ConciliationSummaryDTO(
                c.getId(),
                c.getSale().getNsu(),
                c.getExpectedAmount(),
                c.getReceivedAmount(),
                c.getDifferenceAmount(),
                c.getDivergencyType()
        );
    }
}
