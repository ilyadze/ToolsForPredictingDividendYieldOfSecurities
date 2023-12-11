package com.dashko.common.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonCreateDTO {
    String email;
    String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String firstname;
    String lastname;
}
