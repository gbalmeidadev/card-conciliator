package com.conciliator.card_conciliator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Builder.Default
    @Column(name = "active")
    private Boolean active = true;

    @ManyToMany(mappedBy = "perfis", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> users = new HashSet<>();
}