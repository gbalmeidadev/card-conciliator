package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @Column(name = "codigo_venda", unique = true, nullable = false)
    private String saleCode;

    @Column(name = "nsu")
    private String nsu;

    @Column(name = "codigo_autorizacao")
    private String authorizationCode;

    @Column(name = "valor_bruto", nullable = false, precision = 15, scale = 2)
    private BigDecimal grossAmount;

    @Column(name = "valor_liquido", precision = 15, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "quantidade_parcelas")
    private Integer installment;

    @Column(name = "data_venda", nullable = false)
    private LocalDate saleDate;

    @Column(name = "bandeira")
    private String cardBrand;

    @Column(name = "tipo_transacao")
    private String transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Installment> installmentCount = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "sale")
    private Set<Conciliation> conciliation = new HashSet<>();

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
