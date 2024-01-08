package com.iche.xpresspayapi.dto.request.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;


import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record UserPurchaseAirtimeVTURequest(
        @JsonProperty(value = "phone_number", required = true) @NotBlank String phoneNumber,
        @JsonProperty(value = "amount", required = true) @NotBlank BigDecimal amount,
        @JsonProperty(value = "network_provider", required = true) @NotEmpty(message = "network provider cannot be empty") String networkProvider) implements Serializable {
}
