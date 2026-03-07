package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Enterprise enterprise;

    @Column(name = "codigo_transacao", nullable = false)
    private String transactionCode;

    @Column(name = "nsu")
    private String nsu;

    @Column(name = "codigo_autorizacao")
    private String authorizationCode;

    @Column(name = "bandeira")
    private String cardBrand;

    @Column(name = "tipo_transacao")
    private String transactionType;

    @Column(name = "valor_bruto", nullable = false, precision = 15, scale = 2)
    private BigDecimal grossAmount;

    @Column(name = "valor_liquido", precision = 15, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "quantidade_parcelas")
    private Integer installment;

    @Column(name = "data_transacao", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "nome_operadora")
    private String operatorName;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
