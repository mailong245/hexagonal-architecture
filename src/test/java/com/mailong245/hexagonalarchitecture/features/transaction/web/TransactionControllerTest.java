package com.mailong245.hexagonalarchitecture.features.transaction.web;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.features.transaction.app.TransactionService;
import com.mailong245.hexagonalarchitecture.features.transaction.web.request.CreateTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Test
    void testGetAllTransaction() {
        List<Transaction> transactions = List.of(Transaction.builder().build(), Transaction.builder().build());
        when(transactionService.getAllTransaction()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransaction();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    void testGetTransactionById() {
        String id = "123";
        Transaction transaction = Transaction.builder().build();
        when(transactionService.getTransactionById(id)).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.getTransactionById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testCreateTransaction() {
        CreateTransactionRequest request = new CreateTransactionRequest("user1", "10");

        Transaction createdTransaction = Transaction.builder().build();
        when(transactionService.createTransaction(request)).thenReturn(createdTransaction);

        ResponseEntity<Object> response = transactionController.createTransaction(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdTransaction, response.getBody());
    }

}
