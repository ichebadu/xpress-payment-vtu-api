package com.iche.xpresspayapi.dto.response.userRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseAirtimeResponse implements Serializable {
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("transaction_status")
    private String transactionStatus;
}
