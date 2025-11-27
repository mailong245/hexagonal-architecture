package com.mailong245.hexagonalarchitecture.features.transaction.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateTransactionRequest(
        @JsonProperty("user_id")
        @NotBlank(message = "User ID is required")
        String userId,

        @Min(value = 1, message = "Amount must be greater than 0")
        @NotBlank
        @JsonProperty("amount")
        String amount) {
}


