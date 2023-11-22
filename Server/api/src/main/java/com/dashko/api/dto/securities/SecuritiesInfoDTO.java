package com.dashko.api.dto.securities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecuritiesInfoDTO {
    String logo;
    String symbol;
    String companyName;
    String exchange;
    String industry;
    String website;
    String description;
    String CEO;
    String securityName;
    String sector;
    Integer employees;
    String address;
    String city;
    String state;
    String country;
}
