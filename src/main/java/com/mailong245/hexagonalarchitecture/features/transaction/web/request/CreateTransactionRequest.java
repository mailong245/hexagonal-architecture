package com.mailong245.hexagonalarchitecture.features.transaction.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotBlank
    @JsonProperty("user_id")
    private String userId;

    @Min(1)
    @NotBlank
    @JsonProperty("amount")
    private String amount;
}


