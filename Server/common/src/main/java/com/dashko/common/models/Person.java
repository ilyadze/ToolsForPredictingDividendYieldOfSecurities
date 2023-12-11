package com.dashko.common.models;


import com.dashko.common.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    @SequenceGenerator(name = "person_seq",
            sequenceName = "person_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "id", nullable = false)
    Long id;

    @Email
    @Column(unique = true)
    @Size(max = 100)
    String email;


    String username;

    String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> role;

    String firstname;
    String lastname;
    boolean enabled;
    Timestamp createdAt;
    Timestamp updatedAt;
    String activationCode;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Security> securities;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}
