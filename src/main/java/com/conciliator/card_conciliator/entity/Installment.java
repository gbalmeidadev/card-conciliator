package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "installments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(name = "numero_parcela", nullable = false)
    private Integer installmentNumber;

    @Column(name = "quantidade_parcelas")
    private Integer installmentCount;

    @Column(name = "valor_parcela", precision = 15, scale = 2)
    private BigDecimal installmentAmount;

    @Column(name = "valor_taxa", precision = 15, scale = 2)
    private BigDecimal feeAmount;

    @Column(name = "valor_liquido", precision = 15, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InstallmentStatus status;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
}
