package com.mailong245.hexagonalarchitecture.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record User(

        @JsonProperty("id")
        Long id,

        @JsonProperty("username")
        String username,

        @JsonProperty("email")
        String email) {
}
