package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa.TransactionRepositoryJpaAdapter;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.TransactionEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryJpaAdapterTest {

    @InjectMocks
    private TransactionRepositoryJpaAdapter transactionRepositoryJpaAdapter;

    @Mock
    private TransactionEntityRepository transactionEntityRepository;


    @Test
    void testGetTransactionList() {
        TransactionEntity transactionEntity1 = TransactionEntity.builder()
                .transactionId(1L)
                .user(UserEntity.builder()
                        .id(1L)
                        .build())
                .build();

        TransactionEntity transactionEntity2 = TransactionEntity.builder()
                .transactionId(2L)
                .user(UserEntity.builder()
                        .id(2L)
                        .build())
                .build();
        List<TransactionEntity> entities = Arrays.asList(transactionEntity1, transactionEntity2);

        when(transactionEntityRepository.findAll()).thenReturn(entities);

        List<Transaction> result = transactionRepositoryJpaAdapter.getTransactionList();

        assertEquals(2, result.size());
    }

    @Test
    void testGetTransactionDetailById_Found() {
        String id = "1";
        TransactionEntity transaction = TransactionEntity.builder()
                .transactionId(1L)
                .user(UserEntity.builder()
                        .id(1L)
                        .build())
                .build();

        when(transactionEntityRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionRepositoryJpaAdapter.getTransactionDetailById(id);

        assertNotNull(result);
    }

    @Test
    void testGetTransactionDetailById_NotFound() {
        String id = "2";
        when(transactionEntityRepository.findById(2L)).thenReturn(Optional.empty());

        Transaction result = transactionRepositoryJpaAdapter.getTransactionDetailById(id);

        assertNull(result);
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = Transaction.builder()
                .user(User.builder()
                        .id(1L)
                        .email("test@example.com")
                        .username("testuser")
                        .build())
                .amount("100")
                .build();

        TransactionEntity savedEntity = TransactionEntity.builder()
                .transactionId(1L)
                .user(UserEntity.builder()
                        .id(1L)
                        .email("test@example.com")
                        .username("testuser")
                        .build())
                .amount("100")
                .build();

        when(transactionEntityRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

        Transaction result = transactionRepositoryJpaAdapter.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(1L, result.transactionId());
        assertEquals(1L, result.user().id());
        assertEquals("100", result.amount());
    }

}
