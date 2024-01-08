package com.iche.xpresspayapi.dto.request.airtimeVTU;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtimeVTUPurchaseRequestAPI implements Serializable {
    @JsonProperty(value = "requestId", required = true)
    private Long requestId;

    @JsonProperty(value = "uniqueCode", required = true)
    private String uniqueCode;
    @JsonProperty("details")
    @Embedded
    private Details details;
}
