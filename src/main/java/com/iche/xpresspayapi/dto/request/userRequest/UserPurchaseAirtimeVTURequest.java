package com.iche.xpresspayapi.dto.request.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;


import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record UserPurchaseAirtimeVTURequest(
        @JsonProperty(value = "phone_number", required = true)
        @Column(name = "phone_number", nullable = false)
        @NotBlank(message = "Email address must not be empty")
        @Pattern(regexp = "^[^?*!\\\\/$%^()~<>?\":}{\\[\\]|+=_\\-&#@.,;]+$", message = "Invalid phone number format")
        @Size(min = 11, message = "phoneNumber must be at least 11 characters long")
        String phoneNumber,
        @JsonProperty(value = "amount", required = true)
        BigDecimal amount,
        @JsonProperty(value = "network_provider", required = true)
        @NotBlank(message = "network provider cannot be empty")
        String networkProvider
) implements Serializable {
}
