package com.mailong245.hexagonalarchitecture.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record Transaction(

        @JsonProperty("transaction_id")
        Long transactionId,

        @JsonProperty("user")
        User user,

        @JsonProperty("amount")
        String amount,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt) {

}
