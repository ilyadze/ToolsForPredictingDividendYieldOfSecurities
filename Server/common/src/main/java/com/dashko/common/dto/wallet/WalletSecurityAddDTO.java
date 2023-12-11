package com.dashko.common.dto.wallet;

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
public class WalletSecurityAddDTO {
    String name;
    String symbol;
    Double price;
    String currency;
    Integer quantity;
    String dateOfPurchase;
}
