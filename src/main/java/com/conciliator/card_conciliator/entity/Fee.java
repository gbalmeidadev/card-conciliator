package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_taxa", nullable = false)
    private FeeType type;

    @Column(name = "bandeira")
    private String cardBrand;

    @Column(name = "tipo_transacao")
    private String transactionType;

    @Column(name = "max_parcelas")
    private Integer maxInstallments;

    @Column(name = "porcentagem", precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "valor_fixo", precision = 10, scale = 2)
    private BigDecimal fixedValue;

    @Builder.Default
    @Column(name = "ativo")
    private Boolean active = true;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
