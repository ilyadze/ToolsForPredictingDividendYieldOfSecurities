package com.dashko.common.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Builder(toBuilder = true)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Security {
    @Id
    @SequenceGenerator(name = "person_seq",
            sequenceName = "person_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "id", nullable = false)
    Long id;

    String name;
    String symbol;
    Double price;
    Double totalPrice;
    String currency;
    Integer quantity;
    String dateOfPurchase;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

}
