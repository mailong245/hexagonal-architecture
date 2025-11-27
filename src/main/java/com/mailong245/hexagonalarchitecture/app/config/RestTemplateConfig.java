package com.mailong245.hexagonalarchitecture.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    public static final String GET_TRANSACTION_URL = "/ext/transaction";

    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate extTransactionRestTemplate() throws URISyntaxException, JsonProcessingException {
        RestTemplate extTransactionRestTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(extTransactionRestTemplate);

        Transaction transaction1 = Transaction.builder()
                .user(User.builder()
                        .id(1L)
                        .username("username1")
                        .email("username1@test.com")
                        .build())
                .amount("1000")
                .transactionId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Transaction transaction2 = Transaction.builder()
                .user(User.builder()
                        .id(2L)
                        .username("username2")
                        .email("username2@test.com")
                        .build())
                .amount("1000")
                .transactionId(2L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        mockServer.expect(MockRestRequestMatchers.requestTo(new URI(GET_TRANSACTION_URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsBytes(List.of(transaction1, transaction2))));

        return extTransactionRestTemplate;
    }
}
