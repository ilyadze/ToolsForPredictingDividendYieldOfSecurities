package com.dashko.common.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder(toBuilder = true)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityPurchase {
    @Id
    @SequenceGenerator(name = "person_seq",
            sequenceName = "person_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "id", nullable = false)
    Long id;

    Double purchasePrice;
    Integer quantity;
    String purchaseDate;

    @ManyToOne
    @JoinColumn(name = "security_id")
    Security security;
}
