package com.dashko.api.dto.wallet;

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
public class WalletSecurityGetDTO {
    String name;
    String symbol;
    Double price;
    Double actualPrice;
    Double totalPrice;
    Double priceChange;
    String currency;
    Integer quantity;
}
