package com.iche.xpresspayapi.dto.request.airtimeVTU;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Details {
    @JsonProperty(value = "phoneNumber", required = true)
    private String phoneNumber;
    @JsonProperty(value = "amount", required = true)
    private BigDecimal amount;
}
