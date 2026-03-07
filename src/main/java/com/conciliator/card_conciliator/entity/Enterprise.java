package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "enterprises")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "razao_social", nullable = false)
    private String socialReason;

    @NotBlank
    @Column(name = "nome_fantasia")
    private String fantasyName;

    @NotBlank
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "ativo")
    @Builder.Default
    private Boolean active = true;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Sale> sales;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}