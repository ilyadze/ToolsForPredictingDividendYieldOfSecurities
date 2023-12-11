package com.dashko.common.models;

import jakarta.persistence.*;

@Entity
public class SecurityPurchase {
    @Id
    @SequenceGenerator(name = "person_seq",
            sequenceName = "person_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    private Double purchasePrice;
    private Integer quantity;
    private String purchaseDate;

    @ManyToOne
    @JoinColumn(name = "security_id")
    private Security security;
}
