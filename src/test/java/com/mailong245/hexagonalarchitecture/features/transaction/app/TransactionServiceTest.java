package com.mailong245.hexagonalarchitecture.features.transaction.app;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.external.NotificationService;
import com.mailong245.hexagonalarchitecture.domain.port.external.PaymentService;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.UserRepository;
import com.mailong245.hexagonalarchitecture.features.transaction.web.request.CreateTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;


    @Test
    void createTransaction_success() throws InterruptedException {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setUserId("user1");
        request.setAmount("10");

        User user = new User();
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        Transaction savedTransaction = Transaction.builder().user(user).amount("10").build();
        when(transactionRepository.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createTransaction(request);

        assertEquals(savedTransaction, result);
        verify(paymentService).charges(BigDecimal.TEN);
        verify(notificationService).sendNotification(anyString());
    }

    @Test
    void createTransaction_interruptedException() throws InterruptedException {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setUserId("user1");
        request.setAmount("10");

        User user = new User();
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        Transaction transaction = Transaction.builder().user(user).amount("10").build();

        when(transactionRepository.createTransaction(any(Transaction.class))).thenReturn(transaction);

        doThrow(new InterruptedException()).when(paymentService).charges(any(BigDecimal.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.createTransaction(request);
        });
        assertTrue(exception.getCause() instanceof InterruptedException);
    }

    @Test
    void getAllTransaction_returnsTransactionList() {
        List<Transaction> transactions = List.of(
                Transaction.builder().amount("10").build(),
                Transaction.builder().amount("20").build()
        );
        when(transactionRepository.getTransactionList()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransaction();

        assertEquals(transactions, result);
        verify(transactionRepository).getTransactionList();
    }

    @Test
    void getTransactionById_returnsTransaction() {
        Transaction transaction = Transaction.builder().amount("10").build();
        when(transactionRepository.getTransactionDetailById("tx1")).thenReturn(transaction);

        Transaction result = transactionService.getTransactionById("tx1");

        assertEquals(transaction, result);
        verify(transactionRepository).getTransactionDetailById("tx1");
    }

}
