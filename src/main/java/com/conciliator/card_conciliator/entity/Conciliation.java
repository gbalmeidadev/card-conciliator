package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conciliations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conciliation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installment_id", nullable = false)
    private Installment installment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_transaction_id", nullable = false)
    private OperationTransaction operationTransaction;

    @Column(name = "expected_amount", precision = 15, scale = 2)
    private BigDecimal expectedAmount;

    @Column(name = "received_amount", precision = 15, scale = 2)
    private BigDecimal receivedAmount;

    @Column(name = "difference_amount", precision = 15, scale = 2)
    private BigDecimal differenceAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "divergency_type")
    private DivergenceType divergencyType;

    @Column(name = "notes", length = 500)
    private String notes;

    @Builder.Default
    @Column(name = "conciliation_date", updatable = false)
    private LocalDateTime conciliationDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
