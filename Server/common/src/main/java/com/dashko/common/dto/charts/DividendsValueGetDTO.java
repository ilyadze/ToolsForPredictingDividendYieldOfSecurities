package com.dashko.common.dto.charts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DividendsValueGetDTO {
    String label;
    String date;
    Double dividend;
}
