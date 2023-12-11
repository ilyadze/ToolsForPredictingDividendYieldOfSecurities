package com.dashko.api.dto.charts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SecurityPriceGetDTO {
    String date;
    @JsonProperty("open")
    Double openPrice;
    @JsonProperty("low")
    Double lowPrice;
    @JsonProperty("high")
    Double highPrice;
    @JsonProperty("close")
    Double closePrice;
    Long volume;
}
