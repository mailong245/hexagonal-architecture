package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa.TransactionRepositoryJpaAdapter;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.TransactionEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryJpaAdapterTest {

    @InjectMocks
    private TransactionRepositoryJpaAdapter transactionRepositoryJpaAdapter;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private TransactionEntityRepository transactionEntityRepository;


    @Test
    void testGetTransactionList() {
        TransactionEntity entity1 = new TransactionEntity();
        TransactionEntity entity2 = new TransactionEntity();
        List<TransactionEntity> entities = Arrays.asList(entity1, entity2);

        Transaction transaction1 = Transaction.builder().build();
        Transaction transaction2 = Transaction.builder().build();

        when(transactionEntityRepository.findAll()).thenReturn(entities);
        when(modelMapper.map(entity1, Transaction.class)).thenReturn(transaction1);
        when(modelMapper.map(entity2, Transaction.class)).thenReturn(transaction2);

        List<Transaction> result = transactionRepositoryJpaAdapter.getTransactionList();

        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
    }

    @Test
    void testGetTransactionDetailById_Found() {
        String id = "1";
        TransactionEntity entity = new TransactionEntity();
        Transaction transaction = Transaction.builder().build();

        when(transactionEntityRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, Transaction.class)).thenReturn(transaction);

        Transaction result = transactionRepositoryJpaAdapter.getTransactionDetailById(id);

        assertNotNull(result);
        assertEquals(transaction, result);
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
        Transaction transaction = Transaction.builder().build();
        TransactionEntity entity = new TransactionEntity();
        TransactionEntity savedEntity = new TransactionEntity();
        Transaction savedTransaction = Transaction.builder().build();

        when(modelMapper.map(transaction, TransactionEntity.class)).thenReturn(entity);
        when(transactionEntityRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, Transaction.class)).thenReturn(savedTransaction);

        Transaction result = transactionRepositoryJpaAdapter.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(savedTransaction, result);
    }

}
