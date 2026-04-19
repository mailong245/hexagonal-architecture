package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.TransactionEntityRepository;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRepositoryJpaAdapter implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;

    @Override
    public List<Transaction> getTransactionList() {
        return transactionEntityRepository.findAll()
                .stream()
                .map(transactionEntity -> Transaction.builder()
                        .transactionId(transactionEntity.getTransactionId())
                        .user(User.builder()
                                .id(transactionEntity.getUser().getId())
                                .email(transactionEntity.getUser().getEmail())
                                .username(transactionEntity.getUser().getUsername())
                                .build())
                        .amount(transactionEntity.getAmount())
                        .createdAt(transactionEntity.getCreatedAt())
                        .updatedAt(transactionEntity.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public Transaction getTransactionDetailById(String id) {
        return transactionEntityRepository.findById(Long.valueOf(id))
                .map(transactionEntity -> Transaction.builder()
                        .transactionId(transactionEntity.getTransactionId())
                        .user(User.builder()
                                .id(transactionEntity.getUser().getId())
                                .email(transactionEntity.getUser().getEmail())
                                .username(transactionEntity.getUser().getUsername())
                                .build())
                        .amount(transactionEntity.getAmount())
                        .createdAt(transactionEntity.getCreatedAt())
                        .updatedAt(transactionEntity.getUpdatedAt())
                        .build())
                .orElse(null);
    }

    @Transactional(
            isolation = Isolation.READ_UNCOMMITTED
    )
    @Override
    public Transaction createTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .user(UserEntity.builder()
                        .id(transaction.user().id())
                        .build())
                .amount(transaction.amount())
                .build();
        TransactionEntity saved = transactionEntityRepository.save(transactionEntity);
        log.info("Save successful");
        return Transaction.builder()
                .transactionId(saved.getTransactionId())
                .user(User.builder()
                        .id(saved.getUser().getId())
                        .email(saved.getUser().getEmail())
                        .username(saved.getUser().getUsername())
                        .build())
                .amount(saved.getAmount())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

}
