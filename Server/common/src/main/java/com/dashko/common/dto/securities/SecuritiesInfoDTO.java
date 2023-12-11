package com.dashko.common.dto.securities;

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
    String image;
    String symbol;
    String price;
    String changes;
    String companyName;
    String currency;
    String exchange;
    String exchangeShortName;
    String industry;
    String website;
    String description;
    String ceo;
    String sector;
    String country;
    String address;
    String city;
    String state;
}
