package com.dashko.api.dto.person;

import com.dashko.common.models.enums.Role;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.Set;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonGetDTO {
    String email;
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Set<Role> role;
    String firstname;
    String lastname;
}
