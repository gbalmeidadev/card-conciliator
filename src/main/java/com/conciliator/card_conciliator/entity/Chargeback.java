package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "chargebacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chargeback {
    
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

    @Column(name = "amount_chargeback", precision = 15, scale = 2)
    private BigDecimal amountChargeback;

    @Column(name = "reason")
    private String reason;

    @Column(name = "reason_code")
    private String reasonCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChargebackStatus status;

    @Column(name = "chargeback_date")
    private LocalDate chargebackDate;

    @Column(name = "data_limite")
    private LocalDate deadLineDate;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
